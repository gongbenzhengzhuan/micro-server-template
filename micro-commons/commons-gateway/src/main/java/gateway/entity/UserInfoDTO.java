package gateway.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 尚光中
 * @date 2023-04-03 20:03:29
 * @description 用户角色信息传输类
 */
@Data
@ApiModel(value = "用户角色信息传输类")
public class UserInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("格尔用户id")
    private String geerUserId;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("用户id")
    private String userid;

    @ApiModelProperty("所属部门")
    private String department;

    @ApiModelProperty("所属组织")
    private String organizationName;

    @ApiModelProperty("所属角色")
    private String roleName;

    @ApiModelProperty("格尔网关 用户编号")
    private String keyNum;

    @ApiModelProperty("客户端请求ip")
    private String ip;

}
