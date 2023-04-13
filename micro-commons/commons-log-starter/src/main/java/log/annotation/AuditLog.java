package log.annotation;

import log.enumeration.OperationTypeEnum;
import log.enumeration.SubSystemEnum;

import java.lang.annotation.*;

/**
 * @author sgz
 * @date 2023/3/22 18:28
 * @Deception 审计日志自定义注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {

    /**
     * 子系统
     */
    SubSystemEnum subSystemName() default SubSystemEnum.UNKNOWN_PLATFORM;

    /**
     * 所属模块
     */
    String moduleName() default "未知模块";

    /**
     * 操作类型
     */
    OperationTypeEnum operationType() default OperationTypeEnum.UNKNOWN_OPERATION;

    /**
     * 操作内容
     */
    String operationContent() default "未知内容";
}
