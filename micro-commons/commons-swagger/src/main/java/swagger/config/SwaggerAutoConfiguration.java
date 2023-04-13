package swagger.config;

import cn.hutool.core.collection.CollectionUtil;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Swagger3配置Bean
 *
 * @author lizhongpeng
 * @date 2021/07/23 14:53
 */
@Configuration
@EnableOpenApi
@EnableKnife4j
@EnableAutoConfiguration
@Import(BeanValidatorPluginsConfiguration.class)
@ConditionalOnProperty(prefix = "knife4j", value = {"enable"}, havingValue = "true")
public class SwaggerAutoConfiguration {
    private final OpenApiExtensionResolver openApiExtensionResolver;

    @Resource
    private SwaggerProperties swaggerProperties;

    public SwaggerAutoConfiguration(OpenApiExtensionResolver openApiExtensionResolver) {
        this.openApiExtensionResolver = openApiExtensionResolver;
    }

    @Bean(value = "authDocApi")
    public Docket authDocApi() {
        List<SecurityScheme> securitySchemes = new ArrayList<>();

        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        List<SecurityContext> securityContexts = Arrays.asList(SecurityContext.builder()
                .securityReferences(CollectionUtil.newArrayList(new SecurityReference(swaggerProperties.getAuthKey(), authorizationScopes)))
                .forPaths(PathSelectors.regex("/.*"))
                .build());
        HttpAuthenticationScheme httpAuthenticationScheme = HttpAuthenticationScheme.JWT_BEARER_BUILDER
                .name(swaggerProperties.getAuthKey())
                .description(swaggerProperties.getAuthKey())
                .build();
        securitySchemes.add(httpAuthenticationScheme);

        //默认全局参数
        List<RequestParameter> requestParameters = new ArrayList<>();
        requestParameters.add(new RequestParameterBuilder().name("test").description("测试").in(ParameterType.QUERY).required(true).build());

        Docket docket = new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                //分组名称
                .groupName(swaggerProperties.getGroupName())
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(PathSelectors.any())
                .build()
                .extensions(openApiExtensionResolver.buildSettingExtensions())
                .securitySchemes(security());
        //.securityContexts(securityContexts).securitySchemes(securitySchemes);
        return docket;
    }

    /**
     * 安全认证参数
     *
     * @return
     */
    private List<SecurityScheme> security() {
        List<SecurityScheme> apiKeys = new ArrayList<>();
        apiKeys.add(new ApiKey(swaggerProperties.getAuthKey(), swaggerProperties.getAuthKey(), "header"));
        return apiKeys;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .contact(new Contact(swaggerProperties.getContact().getName(), swaggerProperties.getContact().getUrl(), swaggerProperties.getContact().getEmail()))
                .version(swaggerProperties.getVersion())
                .build();
    }
}