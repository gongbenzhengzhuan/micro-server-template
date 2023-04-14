package gateway.interceptor;


import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import gateway.constants.LoginConstant;
import gateway.constants.SysProp;
import gateway.context.RequestContext;
import gateway.entity.CorlDataVO;
import gateway.entity.CorlResp;
import gateway.entity.Result;
import gateway.entity.UserInfoDTO;
import gateway.utils.HttpServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class WebInterceptor implements HandlerInterceptor {

    private static final String GET_USER_INFO_IP = "";

    @Autowired
    private SysProp sysProp;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String ipAddr = request.getHeader("x-forwarded-for");
        String userAgent = request.getHeader("user-agent");
        log.info("------------x-forwarded-for:" + ipAddr);
        log.info("------------user-agent:" + userAgent);
        if (!userAgent.contains("Mozilla") && userAgent.contains("Java")) {
            return true;
        }

        String requestId = getRequestId(request);
        MDC.put("requestId", requestId);
        String url = request.getRequestURI();
        HttpSession session = request.getSession();
        log.info("url:" + url);
        if ("/error".equals(url)) {
            return false;
        }
        log.info("session.id:" + session.getId());
        Object attribute = session.getAttribute(LoginConstant.USER);
        UserInfoDTO user = null;
        if (!ObjectUtils.isEmpty(attribute)) {
            user = JSONObject.parseObject(JSONObject.toJSONString(attribute), UserInfoDTO.class);
            System.out.println(user.getKeyNum());
        }
        log.info("session:" + JSONObject.toJSONString(session.getAttributeNames()));
//		if(!sysProp.isGeerIsUse()|| StringUtils.isBlank(HttpServletUtils.getCookie(sysProp.getGeerUserKey(), request.getCookies())))
//        if (sysProp.isGeerIsUse()) {//格尔网关
        if (true) {//格尔网关
            boolean refresh = false;//前端是否刷新
            String errorMsg = null;//报错信息
            try {
                String keyNum;
                if (sysProp.isGeerIsUse()) {
                    keyNum = HttpServletUtils.getCookieAndAssert(sysProp.getGeerUserKey(), request.getCookies());
                } else {
                    keyNum = "Gol123456";
                }
                log.info("keyNum:" + keyNum);
                Long loginTime = (Long) request.getSession().getAttribute(LoginConstant.LOGIN_TIME);
                if (user == null || (loginTime != null && loginTime > (System.currentTimeMillis() + sysProp.getGeerLoginExpireTime()))) {
                    //用户不存在或用户登录信息过期时 登录
                    String ip;
                    if (sysProp.isGeerIsUse()) {
                        ip = new String(URLDecoder.decode(HttpServletUtils.getCookieAndAssert(sysProp.getGeerIpKey(), request.getCookies())).getBytes("ISO-8859-1"), "GBK");
                    } else {
                        ip = "192.168.0.1";
                    }
                    //获取用户信息
                    UserInfoDTO userRoleInfoDTO = new UserInfoDTO();
                    userRoleInfoDTO.setGeerUserId("122");
                    userRoleInfoDTO.setIp("123");
                    userRoleInfoDTO.setKeyNum("Gol123456");

                    if (ObjectUtils.isEmpty(userRoleInfoDTO)) {
                        throw new IllegalArgumentException("未找到此key对应的用户信息!");
                    }
                    JSONObject res = new JSONObject();
                    res.put("userInfo", userRoleInfoDTO);
                    res.put("keyNumber", keyNum);
                    res.put("ip", ip);
                    loginByUserDataOfZYC(res, null, session);
                } else if (!keyNum.equals(user.getKeyNum())) {
                    //userKey不一致 客户端切换用户 后端清除session记录 前端刷新页面
                    session.invalidate();
                    refresh = true;
                } else {
                    session.setAttribute(LoginConstant.USER, user);
                }
                //todo 如果ip或mac地址不同，是否需要校验？
            } catch (Exception e) {
                e.printStackTrace();
                errorMsg = e.getMessage();
            }
            if (refresh || errorMsg != null) {
                response.setStatus(HttpStatus.OK.value());
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                JSONObject res = new JSONObject();
                res.put("status", 401);
                res.put("refresh", refresh);
                res.put("errorMsg", errorMsg);
                PrintWriter out = response.getWriter();
                out.write(res.toString());
                out.flush();
                out.close();
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {
        //
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        // 请求完成之后把requestId给清理掉
        RequestContext.clearRequestId();
    }

    // 为每次请求生成唯一的请求id
    private static String getRequestId(HttpServletRequest request) {
        String requestId;
        String parameterRequestId = request.getParameter(LoginConstant.REQUEST_ID_KEY);
        String headerRequestId = request.getHeader(LoginConstant.REQUEST_ID_KEY);

        if (parameterRequestId == null && headerRequestId == null) {
            requestId = UUID.randomUUID().toString();
        } else {
            requestId = parameterRequestId != null ? parameterRequestId : headerRequestId;
        }
        RequestContext.setRequestId(requestId);
        return requestId;
    }

//	private String getRedisTokenKey(String token){
//		return PermConstant.SYSTEM_CODE+"_"+LoginConstant.TOKEN+"_"+token;
//	}

//	private UserInfo loginByUserData(JSONObject data, String token, HttpSession session){
//		HashMap<String, Set<String>> userPerm=new HashMap<>();
//		JSONObject userInfo = data.getJSONObject("userInfo");
//		JSONObject groupInfo = data.getJSONObject("groupInfo");
//		UserInfo user = new UserInfo();
//		user.setId(userInfo.getString("userId"));
//		user.setUsername(userInfo.getString("userName"));
//		user.setName(userInfo.getString("dispalyName"));
//		user.setKeyNum("12345");
//		user.setIpAddr(userInfo.getString("ipAddr"));
//		user.setGroupId(groupInfo.getString("groupId"));
//		user.setGroupName(groupInfo.getString("groupName"));
//		user.setCenterBln(StringUtils.isBlank(groupInfo.getString("parentGroupId")));
//		JSONArray permInfoList = data.getJSONArray("permInfoList");
//		for (int i = 0; i < permInfoList.size(); i++) {
//			JSONObject jsonObject = permInfoList.getJSONObject(i);
//			String key = jsonObject.getString("permCode");
//			JSONArray children = jsonObject.getJSONArray("children");
//			HashSet<String> permCodes = new HashSet<>();
//			if(children!=null){
//				for (int i1 = 0; i1 < children.size(); i1++) {
//					JSONObject jsonObject1 = children.getJSONObject(i1);
//					permCodes.add(jsonObject1.getString("permCode"));
//				}
//			}
//			userPerm.put(key,permCodes);
//		}
//		session.setAttribute(LoginConstant.USER, user);
//		Set<String> perms=userPerm.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
//		session.setAttribute(LoginConstant.MENU_PREM_LIST, userPerm);
//		session.setAttribute(LoginConstant.PERM_LIST, perms);
//		session.setAttribute(LoginConstant.LOGIN_TIME, System.currentTimeMillis());//登录时间
////		if(token!=null){
////			redisTemplate.opsForValue().set(getRedisTokenKey(token),System.currentTimeMillis(),Duration.ofDays(1));
////			session.setAttribute(LoginConstant.TOKEN, token);
////		}
//		return user;
//	}

    //todo
    private UserInfoDTO loginByUserDataOfZYC(JSONObject data, String token, HttpSession session) {
        String ip = data.getString("ip");
        //如果DB中IP字段为0.0.0.0，则不校验数据库中的ip
        if (!"0.0.0.0".equals(ip)) {
            //todo 不为0.0.0.0，则去校验此次登录ip是否与DB中配置相同
        }
        UserInfoDTO user = data.getObject("userInfo", UserInfoDTO.class);
        user.setIp(ip);
        session.setAttribute(LoginConstant.USER, JSONObject.parseObject(JSONObject.toJSONString(user)));
        session.setAttribute(LoginConstant.GEER_KEY_NUMBER, data.getString("keyNumber"));
        session.setAttribute(LoginConstant.LOGIN_TIME, System.currentTimeMillis());//登录时间
        return user;
    }

//    private UserInfoDTO getUserInfoByKeyNum(String KeyNum) {
//        Result userInfoByKeyNum = manageClinent.getUserInfoByKeyNum(KeyNum);
//        UserInfoDTO apiData = JSON.parseObject(JSON.toJSONString(getApiData(userInfoByKeyNum)), UserInfoDTO.class);
//        return apiData;
//    }


    private JSONObject getTicketData(String ticket, String serviceTicketUrl) {
        String str = HttpUtil.get(String.format(sysProp.getSsoCasService() + "/cas/serviceValidate?ticket=%s&service=%s", ticket, serviceTicketUrl));
        JSONObject resJson = JSONObject.parseObject(str);
        JSONObject serviceResponse = resJson.getJSONObject("serviceResponse");
        if (null != serviceResponse.get("authenticationFailure")) {//校验失败抛异常
            log.info(serviceResponse.getString("authenticationFailure"));
        }
        return serviceResponse.getJSONObject("authenticationSuccess").getJSONObject("userDTO");
    }

    private static Object getApiData(Result result) {
        if (result.getCode().equals(200) || result.getCode().equals(0)) {
            if (result.getData() != null) {
                return result.getData();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 通过keyNumber从总集获取用户信息。
     *
     * @param keyNumber
     * @return java.lang.String
     * @author chuzihao
     * @date 2023/4/1
     */
    public CorlDataVO getUserInfoByKeyNumber(String keyNumber) {
        Map map = new HashMap();
        map.put("keyNumber", keyNumber);
        String res = HttpUtil.post("http://" + GET_USER_INFO_IP + "/unify/common/getUserByKey", JSON.toJSONString(map));
        CorlResp corlResp = JSONObject.parseObject(res, CorlResp.class);
        if (ObjectUtils.isNotEmpty(corlResp) && "200".equals(corlResp.getCode())) {
            return corlResp.getData();
        }
        throw new IllegalArgumentException("调用API失败,失败信息:" + (ObjectUtils.isEmpty(corlResp) ? "网络异常" : corlResp.getMsg()));
    }
}

