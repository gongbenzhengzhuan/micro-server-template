package com.micro.manage.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 系统日志表
 * </p>
 *
 * @author 作者
 * @since 2023-04-13
 */
@Getter
@Setter
@TableName("system_log")
@ApiModel(value = "SystemLog对象", description = "系统日志表")
public class SystemLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户姓名")
    private String userName;

    @ApiModelProperty("所属角色名称")
    private String roleName;

    @ApiModelProperty("所属部门名称")
    private String department;

    @ApiModelProperty("子系统")
    private String subsystem;

    @ApiModelProperty("所属模块")
    private String module;

    @ApiModelProperty("操作类型")
    private String operationType;

    @ApiModelProperty("操作结果")
    private String operationResult;

    @ApiModelProperty("ip")
    private String ip;

    @ApiModelProperty("mac地址")
    private String macAddress;

    @ApiModelProperty("key号")
    private String keyNumber;

    @ApiModelProperty("操作时间")
    private Date operationTime;

    @ApiModelProperty("操作日期")
    private Date operationDate;

    @ApiModelProperty("操作内容")
    private String operationContent;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("是否删除，0未删除，1删除")
    @TableLogic
    private Integer deleted;


}
