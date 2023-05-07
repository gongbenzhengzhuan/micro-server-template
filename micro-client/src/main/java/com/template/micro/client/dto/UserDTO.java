package com.template.micro.client.dto;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2023-05-06
 */
@Getter
@Setter
@ApiModel(value = "UserDTO对象", description = "")
public class UserDTO extends PageBaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String login;

    private String password;

    private Integer role;


}
