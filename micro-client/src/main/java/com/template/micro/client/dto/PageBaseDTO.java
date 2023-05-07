package com.template.micro.client.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "分页基础对象", description = "分页基础对象")
public class PageBaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "当前页")
    private long pageIndex;

    @ApiModelProperty(value = "页数据量")
    private long pageSize;

}
