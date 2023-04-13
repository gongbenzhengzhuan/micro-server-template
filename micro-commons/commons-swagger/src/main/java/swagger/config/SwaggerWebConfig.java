package swagger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lizhongpeng
 * @date 2022/05/18 17:13
 */
@Configuration
public class SwaggerWebConfig implements WebMvcConfigurer {

    //@Value("${swagger.prefix}")
    private String swaggerPrefix = "";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(swaggerPrefix + "/**")
                .addResourceLocations("classpath:/static/");

        registry.addResourceHandler(swaggerPrefix + "doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler(swaggerPrefix + "/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /*@Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addRedirectViewController(swaggerPrefix + "/v3/api-docs", "/v3/api-docs").setKeepQueryParams(true);
        registry.addRedirectViewController(swaggerPrefix + "/swagger-resources", "/swagger-resources");
        registry.addRedirectViewController(swaggerPrefix + "/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
        registry.addRedirectViewController(swaggerPrefix + "/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
        registry.addRedirectViewController(swaggerPrefix + "/doc.html", "/doc.html");
    }*/

    /**
     * 跨域配置
     *
     * @param corsRegistry
     */
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders(HttpHeaders.ACCEPT);
        WebMvcConfigurer.super.addCorsMappings(corsRegistry);
    }

}
