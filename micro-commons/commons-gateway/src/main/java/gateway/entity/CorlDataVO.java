package gateway.entity;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

/**
 * @author chuzihao
 * @date 2023/3/29
 * @Deception TODO
 */
@Data
public class CorlDataVO {
    private String keyId;

    private Integer type;

    private String keyNumber;

    private JSONArray userList;
}
