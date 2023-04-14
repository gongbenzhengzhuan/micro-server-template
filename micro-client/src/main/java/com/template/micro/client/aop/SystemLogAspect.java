package com.template.micro.client.aop;

import com.template.micro.client.entity.SystemLog;
import com.template.micro.client.service.ISystemLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author fangxin
 * @Date 2023/1/13
 */
@Component
@Aspect
public class SystemLogAspect {

    @Autowired
    private ISystemLogService iSystemLogService;

    @Pointcut("execution(*  com.template.micro.client.controller.*.*(..))")
    public void pointCutSystemLog() {
    }

    @Around("pointCutSystemLog()")
    public Object pointCutOrg(ProceedingJoinPoint pjd) throws Throwable {
        SystemLog systemLog = new SystemLog();
        systemLog.setUserName("切面测试用户");
        systemLog.setRoleName("切面测试角色");
        systemLog.setOperationType("添加");
//        systemLog.setCreateTime();
        iSystemLogService.save(systemLog);
        Api api = (Api) (pjd.getSignature().getDeclaringType().getAnnotation(Api.class));
        MethodSignature signature = (MethodSignature) pjd.getSignature();
        Method method = signature.getMethod();
        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
        if (api.tags() != null) {
            System.out.println(api.tags()[0] + "--" + apiOperation.value());
        }
        System.out.println("切面测试");
        return pjd.proceed();

    }
}
