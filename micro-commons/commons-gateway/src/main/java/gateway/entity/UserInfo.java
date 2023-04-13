package gateway.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 作者
 * @since 2023-01-04
 */
@Getter
@Setter
@TableName("user_info")
@ApiModel(value = "UserInfo对象", description = "用户-对象")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("用户账号")
    private String userid;

    @ApiModelProperty("所属部门")
    private String department;

    @ApiModelProperty("状态: 0、添加待审批 1、添加失败 2、更新待审批  3、更新失败  4、审批通过")
    private Integer state;


    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//入参
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//出参
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("是否删除，0未删除，1删除")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty("修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//入参
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")//出参
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("格尔网关 用户编号")
    private String keyNum;

    @ApiModelProperty("格尔网关 用户ip")
    private String ipAddr;


}
