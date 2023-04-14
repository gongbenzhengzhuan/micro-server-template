package log.aop;

import api.RemoteLogService;
import api.entity.SearchLogDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import log.annotation.SearchLog;
import log.queue.LogQueue;
import log.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import utils.utils.IpUtil;
import utils.utils.TimeUtils;
import utils.utils.thread.ThreadPoolUtils;
import utils.vo.Result;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author Eric.Wang[王承]
 * @Date 2023-03-28 10:14 星期二
 * @ClassName com.zyc.datasystem.log.aspect.SerachLogAspect
 * @Description: 搜索日志切面类
 */
@SuppressWarnings("all")
@Slf4j
@Aspect
public class SearchLogAspect implements IService<SearchLogDTO> {

    /**
     * 重试次数
     */
    private static final int MAXRETRYTIMES = 5;

    /** 接口请求线程池 */
    private ThreadPoolUtils searchLogPool;

    /** 调用远程服务存储数据线程池 */
    private ThreadPoolUtils saveLogPool;

    @Autowired
    private RemoteLogService remoteLogService;

    @Autowired
    private LogQueue<SearchLogDTO> logQueue;

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-28 10:03:03
     * @Description 注解切入点, 增加 @SearchLog 的方法，仅作为切面调用标记
     * @Return void
     */
    @Pointcut("@annotation(log.annotation.SearchLog)")
    public void pointCut() {
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-28 10:03:57
     * @Description 切面增强方法
     * @Param[0] org.aspectj.lang.ProceedingJoinPoint proceedingJoinPoint
     * @Return java.lang.Object
     */
    @Around(value = "pointCut()")
    public Object searchLogAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return processSearchLog(proceedingJoinPoint);
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-28 10:03:30
     * @Description 切面具体业务处理方法
     * @Param[0] org.aspectj.lang.ProceedingJoinPoint proceedingJoinPoint
     * @Return java.lang.Object
     */
    private Object processSearchLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest httpServletRequest = null;
        Object resultProced = null;
        Date startTime = null;
        Date endTime = null;
        try {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            httpServletRequest = Objects.requireNonNull(servletRequestAttributes).getRequest();
            // 开始时间
            startTime = new Date(System.currentTimeMillis());
            // 执行方法
            resultProced = proceedingJoinPoint.proceed();
            // 结束时间
            endTime = new Date(System.currentTimeMillis());
        } catch (Throwable throwable) {
            // 异常处理
            this.catchExeptionFinal(throwable);
        } finally {
            // 异步存储搜索日志数据，不影响业务执行
            this.asyncSetSearchLogDTO(proceedingJoinPoint, httpServletRequest, resultProced, startTime, endTime);
        }
        return resultProced;
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-29 17:03:07
     * @Description 异常处理，后续需要扩展
     * @Param[0] java.lang.Throwable throwable
     * @Return void
     */
    private void catchExeptionFinal(Throwable throwable) throws Throwable {
        PrintStream printStream = null;
        String exceptionStr = "";
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            printStream = new PrintStream(outputStream);
            throwable.printStackTrace(printStream);
            // 完整异常信息字符串，存储数据库，有长度限制，需要进行截取
            exceptionStr = outputStream.toString();
            // TODO 方便后续存储异常信息到数据库，入库动作，目前数据库没有进行异常处理的情况，后续扩展使用
            throwable.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            printStream.close();
        }
        throw new Throwable(throwable.getMessage(), throwable);
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-28 21:03:03
     * @Description 异步存储搜索日志的数据
     * @Param[0] org.aspectj.lang.ProceedingJoinPoint proceedingJoinPoint
     * @Param[1] javax.servlet.http.HttpServletRequest httpServletRequest
     * @Param[2] java.lang.Object resultProced
     * @Param[3] java.util.Date startTime
     * @Param[4] java.util.Date endTime
     * @Return void
     */
    private void asyncSetSearchLogDTO(ProceedingJoinPoint proceedingJoinPoint, HttpServletRequest httpServletRequest, Object resultProced, Date startTime, Date endTime) throws Exception {
        searchLogPool.execute(() -> {
            for (int reTry = 0; reTry <= MAXRETRYTIMES; reTry++) {
                try {
                    // 获取数据存储DTO
                    SearchLogDTO searchLogDTO = this.setSearchLogDTO(proceedingJoinPoint, httpServletRequest, resultProced, startTime, endTime);
                    // 发送消息
                    this.logQueue.producer(searchLogDTO);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("搜索日志记录放入生产者队列失败，开始重试，重试总次数：" + MAXRETRYTIMES + "，剩余重试次数：" + (MAXRETRYTIMES - reTry));
                    if (reTry >= MAXRETRYTIMES) {
                        // TODO 重试次数用完还是失败，需要进行对应的数据补偿机制
                        throw new RuntimeException("搜索日志记录放入生产者队列重试次数耗尽！");
                    }
                }
            }
        });
    }

    /**
     * 远程存储线程池大小
     */
    private static final int $6 = 6;

    /**
     * HTTP请求线程池大小
     */
    private static final int $10 = 10;

    /**
     * 线程队列大小
     */
    private static final int THREAD_QUEUE = 4 * 1024;

    public void init(){
        // 服务启动时，创建线程池
        this.searchLogPool = new ThreadPoolUtils($10, $10, THREAD_QUEUE, "HTTP请求");
        this.saveLogPool = new ThreadPoolUtils($6, $6, THREAD_QUEUE, "远程存储服务");
    }

    public void save(List<SearchLogDTO> data) {
        if(0 >= data.size()){
            return;
        }
        // 调用搜索日志存储服务接口
        this.saveLogPool.execute(() -> {
            this.remoteLogService.storeSearchLogList(data);
            log.info("-------------数据调用存储服务完成[" + data.size() + "]-------------");
        });
    }

    /**
     * @Author Eric·Wang[王承]
     * @Date 2023-03-28 21:03:35
     * @Description 请求入参赋值
     * @Param[0] org.aspectj.lang.ProceedingJoinPoint proceedingJoinPoint
     * @Param[1] javax.servlet.http.HttpServletRequest httpServletRequest
     * @Param[2] java.lang.Object resultProced
     * @Param[3] java.util.Date startTime
     * @Param[4] java.util.Date endTime
     * @Return com.zyc.datasystem.api.entity.SearchLogDTO
     */
    private SearchLogDTO setSearchLogDTO(ProceedingJoinPoint proceedingJoinPoint, HttpServletRequest httpServletRequest, Object resultProced, Date startTime, Date endTime) throws Exception {
        // 方法执行开始时间
        String start = TimeUtils.date2Str(startTime, "yyyy-MM-dd HH:mm:ss");
        // 方法执行结束时间
        String end = TimeUtils.date2Str(endTime, "yyyy-MM-dd HH:mm:ss");
        // 接口耗时
        String timeResuming = TimeUtils.getTimeResuming(startTime, endTime);
        // 获取目标类字节码对象
        Class<?> targetClass = proceedingJoinPoint.getTarget().getClass();
        // 获取调用方法签名
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        // 方法名称
        String methodName = methodSignature.getName();
        // 方法参数数据类型
        Class[] parameterTypes = methodSignature.getParameterTypes();
        // 拼接调用方法目标类路径+方法名称+开始时间+结束时间+接口耗时
        StringBuilder stringBuilder = new StringBuilder("服务调用➤➤[")
                .append(targetClass.getName()).append(".").append(methodName).append("()")
                .append("]")
                .append("➤➤开始时间：").append(start).append(", ")
                .append("结束时间:").append(end).append(", ")
                .append("耗时：").append(timeResuming);
        // log.info(stringBuilder.toString());
        // 获取方法上的注解方法对象
        Method targetMethod = targetClass.getDeclaredMethod(methodName, parameterTypes);
        // 获取注解对象
        SearchLog searchLog = targetMethod.getAnnotation(SearchLog.class);
        // 获取请求参数名称
        String[] parameterNames = methodSignature.getParameterNames();
        // 获取请求参数
        Object[] parameterValues = proceedingJoinPoint.getArgs();
        // 解析参数，搜索关键字，接口数据格式不统一，直接将所有请求参数存储
        JSONObject parameterObject = new JSONObject();
        for (int i = 0; i < parameterValues.length; i++) {
            String parameterName = parameterNames[i];
            String parameterValue = JSON.toJSONString(parameterValues[i]);
            parameterObject.put(parameterName, parameterValue);
        }
        // 搜索人
        String userName = "";//todo qc 需要修改
        // 搜索关键字
        String searchKeywords = parameterObject.toJSONString();
        Result result = new Result();
        if (null != resultProced && resultProced instanceof Result) {
            result = (Result) resultProced;
        }
        // 命中数量
        Integer searchNum = result.getTotal();
        // 所属角色
        String roles = "";
        // 所属组织
        String department = "";
        // 发起本次搜索的应用
        String application = searchLog.application().getName();
        // 搜索分类
        String searchCategory = searchLog.searchCategories().getName();
        // 终端标识
        String terminalMarking = "";
        // 终端设备名
        String terminalEquipment = "";
        // IP地址
        String ip = IpUtil.getIpAddr(httpServletRequest);
        // MAC地址
        String mac = IpUtil.getMacAddressByIp(ip);
        // key号
        String keyNumber = "";
        // 操作时间
        Date operationTime = new Date();
        // 发起本次搜索的具体内容
        String operationContent = searchLog.operationContent();

        SearchLogDTO searchLogDTO = new SearchLogDTO();
        searchLogDTO.setUserName(userName);//todo qc 需要修改
        searchLogDTO.setSearchKeywords(searchKeywords);
        searchLogDTO.setSearchNum(searchNum);
        searchLogDTO.setRoles(roles);
        searchLogDTO.setDepartment(department);
        searchLogDTO.setApplication(application);
        searchLogDTO.setSearchCategory(searchCategory);
        searchLogDTO.setTerminalMarking(terminalMarking);
        searchLogDTO.setTerminalEquipment(terminalEquipment);
        searchLogDTO.setIp(ip);
        searchLogDTO.setMacAddress(mac);
        searchLogDTO.setKeyNumber(keyNumber);
        searchLogDTO.setOperationTime(operationTime);
        searchLogDTO.setOperationContent(operationContent);
        searchLogDTO.setCreateTime(operationTime);
        searchLogDTO.setUpdateTime(operationTime);
        return searchLogDTO;
    }
}
