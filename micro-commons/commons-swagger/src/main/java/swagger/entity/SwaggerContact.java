package swagger.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Swagger联系人信息
 *
 * @author lizhongpeng
 * @date 2019/09/22 14:41
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SwaggerContact implements Serializable {
    /**
     * 联系人
     **/
    private String name = "";
    /**
     * 联系人url
     **/
    private String url = "";
    /**
     * 联系人email
     **/
    private String email = "";
}
