package gateway.config;

import gateway.interceptor.WebInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 100)
    public HandlerInterceptor getWebInterceptor() {
        return new WebInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getWebInterceptor()).excludePathPatterns(
                "/api/**"
                , "/static/**"
                , "/vendor/**"
                , "/temp/**"
                , "/index.html"
//                    ,"/auth-center/permission/getUserInfoByKeyNum/**"
//                ,"/"
        ).addPathPatterns("/*/**");
    }

//    /**
//     * 处理跨域请求
//     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOriginPatterns("*")// 允许所有的请求地址跨域
//                .allowedMethods("*")
//                .allowedHeaders("*")
//                .allowCredentials(true).maxAge(3600);
//    }
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController( "/" ).setViewName( "forward:/index.html" );
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE );
//    }
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        String basePath = SystemUtil.getUserInfo().getCurrentDir();
//        registry.addResourceHandler("/**").addResourceLocations("file:"+basePath+webFolder);
//        registry.addResourceHandler("/"+tempFolder+"**").addResourceLocations("file:"+basePath+tempFolder);
//        registry.addResourceHandler("/vendor/**").addResourceLocations("classpath:vendor/");
//    }
}
