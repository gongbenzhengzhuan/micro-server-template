package com.template.micro.client.dto;

import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "PermissionDTO对象", description = "")
public class PermissionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String permission;

    private String route;


}
