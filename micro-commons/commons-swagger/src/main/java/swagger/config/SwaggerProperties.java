package swagger.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import swagger.entity.SwaggerAuthorization;
import swagger.entity.SwaggerContact;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger属性文件
 *
 * @author lizhongpeng
 * @date 2021/07/23 14:36
 */
@Data
@Component
@ConfigurationProperties("swagger")
public class SwaggerProperties {
    /**
     * 是否开启swagger
     */
    private Boolean enabled;

    /**
     * swagger会解析的包路径
     **/
    private String basePackage = "";

    /**
     * swagger会解析的url规则
     **/
    private List<String> basePath = new ArrayList<>();

    /**
     * 在basePath基础上需要排除的url规则
     **/
    private List<String> excludePath = new ArrayList<>();

    /**
     * 标题
     **/
    private String title = "";

    /**
     * 版本
     **/
    private String version = "";

    /**
     * 分组
     */
    private String groupName = "";

    /**
     * 描述
     **/
    private String description = "";

    /**
     * 许可证
     **/
    private String license = "";

    /**
     * 许可证URL
     **/
    private String licenseUrl = "";

    /**
     * 服务条款URL
     **/
    private String termsOfServiceUrl = "";

    /**
     * 认证KEY
     */
    private String authKey;

    /**
     * host信息
     **/
    private String host = "";

    /**
     * 联系人信息
     */
    private SwaggerContact contact = new SwaggerContact();

    /**
     * 全局统一鉴权配置
     **/
    private SwaggerAuthorization authorization = new SwaggerAuthorization();
}
