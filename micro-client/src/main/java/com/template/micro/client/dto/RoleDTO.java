package com.template.micro.client.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 作者
 * @since 2023-05-07
 */
@Getter
@Setter
@ApiModel(value = "RoleDTO对象", description = "")
public class RoleDTO extends PageBaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty("角色")
    private String role;

    @ApiModelProperty("权限")
    private Integer permisson;


}
