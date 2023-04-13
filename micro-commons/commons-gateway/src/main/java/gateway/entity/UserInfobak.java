package gateway.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfobak implements Serializable {
    private String id;//用户id
    private String name;//用户名称
    private String username;//用户账号
    private String groupId;//中心id
    private String groupName;//组名称
    private boolean centerBln;//是否是总中心 true是 false否

    private String ipAddr;//格尔网关 用户ip
    private String keyNum;//格尔网关 用户编号
}
