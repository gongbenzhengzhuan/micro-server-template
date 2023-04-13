package swagger.annotation;


import org.springframework.context.annotation.Import;
import swagger.config.SwaggerAutoConfiguration;

import java.lang.annotation.*;

/**
 * Swagger 注解
 *
 * @author lizhongpeng
 * @date 2021/07/23 14:52
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerAutoConfiguration.class})
public @interface EnableBaseSwagger2 {
}
