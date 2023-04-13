package gateway.entity;

import lombok.Data;

/**
 * @author chuzihao
 * @date 2023/3/29
 * @Deception TODO
 */
@Data
public class CorlResp {

    private String code;

    private CorlDataVO data;

    private String msg;
}
