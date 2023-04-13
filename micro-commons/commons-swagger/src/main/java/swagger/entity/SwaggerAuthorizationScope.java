package swagger.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Swagger 鉴权作用域
 *
 * @author lizhongpeng
 * @date 2019/09/22 14:45
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SwaggerAuthorizationScope {
    /**
     * 作用域名称
     */
    private String scope = "";

    /**
     * 作用域描述
     */
    private String description = "";
}
