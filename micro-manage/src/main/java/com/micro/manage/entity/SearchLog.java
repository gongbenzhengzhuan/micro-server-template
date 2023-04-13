package com.micro.manage.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.micro.manage.utils.poi.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Eric.Wang[王承]
 * @Date 2023-03-25 10:04 星期六
 * @ClassName com.zyc.datasystem.manage.entity.SearchLog
 * @Description: 搜索日志实体类
 */
@SuppressWarnings("all")
@Getter
@Setter
@TableName("search_log")
@ApiModel(value = "SearchLog对象", description = "搜索日志表")
public class SearchLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增id")
    @Excel(name = "序号", width = 5)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("搜索人")
    @Excel(name = "搜索人", width = 10)
    private String userName;

    @ApiModelProperty("搜索关键词")
    @Excel(name = "搜索关键词", width = 30)
    private String searchKeywords;

    @ApiModelProperty("命中结果数")
    @Excel(name = "命中结果数")
    private Integer searchNum;

    @ApiModelProperty("所属角色")
    @Excel(name = "所属角色")
    private String roles;

    @ApiModelProperty("所属组织")
    @Excel(name = "所属组织")
    private String department;

    @ApiModelProperty("发起应用")
    @Excel(name = "发起应用")
    private String application;

    @ApiModelProperty("搜索分类")
    @Excel(name = "搜索分类")
    private String searchCategory;

    @ApiModelProperty("终端标识")
    @Excel(name = "终端标识")
    private String terminalMarking;

    @ApiModelProperty("IP地址")
    @Excel(name = "IP地址", width = 12)
    private String ip;

    @ApiModelProperty("MAC地址")
    @Excel(name = "MAC地址")
    private String macAddress;

    @ApiModelProperty("终端设备名")
    @Excel(name = "终端设备名")
    private String terminalEquipment;

    @ApiModelProperty("key号")
    private String keyNumber;

    @ApiModelProperty("操作时间")
    @Excel(name = "操作时间", width = 16, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date operationTime;

    @ApiModelProperty("操作内容")
    private String operationContent;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("是否删除，0未删除，1删除")
    private Integer deleted;

}
