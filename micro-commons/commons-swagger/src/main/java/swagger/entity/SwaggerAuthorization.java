package swagger.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Swagger Authorization
 *
 * @author lizhongpeng
 * @date 2019/09/22 14:44
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SwaggerAuthorization implements Serializable {
    /**
     * 鉴权策略ID
     */
    private String name = "";

    /**
     * 需要开启鉴权URL的正则
     */
    private String authRegex = "^.*$";

    /**
     * 鉴权作用域列表
     */
    private List<SwaggerAuthorizationScope> scopeList = new ArrayList<>();

}
