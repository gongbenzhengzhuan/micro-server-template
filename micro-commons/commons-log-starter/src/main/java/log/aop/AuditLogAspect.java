package log.aop;

import api.RemoteLogService;
import api.entity.SystemLogInsertDTO;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import log.annotation.AuditLog;
import log.constants.LogConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import utils.utils.IpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

/**
 * @author sgz
 * @date 2023/3/22 14:19
 * @Deception 审计日志切面
 */
@Slf4j
@Aspect
@Component
public class AuditLogAspect {

    @Autowired
    private RemoteLogService remoteLogService;

    /**
     * 正常返回后记录日志
     *
     * @param joinPoint 切点
     * @param auditLog  日志注解
     * @throws Throwable 异常
     */
    @AfterReturning(pointcut = "@annotation(auditLog)")
    public void doAfterReturning(JoinPoint joinPoint, AuditLog auditLog) throws Throwable {
        handleLog(joinPoint, auditLog, LogConstants.OPERATION_RESULT_SUCCESS);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     */
    @AfterThrowing(value = "@annotation(auditLog)")
    public void doAfterThrowing(JoinPoint joinPoint, AuditLog auditLog) throws Throwable {
        handleLog(joinPoint, auditLog, LogConstants.OPERATION_RESULT_FAIL);
    }

    /**
     * 处理日志
     *
     * @param joinPoint 切点
     * @param auditLog  auditLog
     */
    private void handleLog(JoinPoint joinPoint, AuditLog auditLog, String operationResult) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        SystemLogInsertDTO dto = new SystemLogInsertDTO();
        // 获取ip及mac地址
        String ipAddr = IpUtil.getIpAddr(request);
        dto.setIp(ipAddr);
        dto.setOperationResult(operationResult);
        dto.setOperationDate(new Date());
        dto.setOperationTime(new Date());
        getAnnotationParams(auditLog, dto);
        getUserInfo(request, dto);
        remoteLogService.insert(dto);
    }

    /**
     * 获取用户信息
     *
     * @param request 本次请求
     * @param dto     日志实体类
     */
    private void getUserInfo(HttpServletRequest request, SystemLogInsertDTO dto) {
        // 获取session信息
        HttpSession session = request.getSession();
        // 从session获取用户信息
        JSONObject user = JSONObject.parseObject(JSONObject.toJSONString(session.getAttribute("user")));
        if (Objects.nonNull(user) && CollectionUtil.isNotEmpty(user)) {
            String username = user.getString("username");
            String department = user.getString("department");
            String roleName = user.getString("roleName");
            String keyNum = user.getString("keyNum");
            dto.setUserName(StringUtils.isNotBlank(username) ? username : "");
            dto.setDepartment(StringUtils.isNotBlank(department) ? department : "");
            dto.setRoleName(StringUtils.isNotBlank(roleName) ? username : "");
            dto.setKeyNumber(StringUtils.isNotBlank(keyNum) ? keyNum : "");
        }
    }

    /**
     * 获取注解参数
     *
     * @param auditLog auditLog
     * @param dto      entity
     */
    private void getAnnotationParams(AuditLog auditLog, SystemLogInsertDTO dto) {
        dto.setModule(auditLog.moduleName());
        dto.setOperationContent(auditLog.operationContent());
        dto.setSubsystem(auditLog.subSystemName().getDescription());
        dto.setOperationType(auditLog.operationType().getDescription());
    }


}
