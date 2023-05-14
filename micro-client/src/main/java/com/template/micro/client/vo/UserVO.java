package com.template.micro.client.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String login;

    private String password;

    private String role;
}
