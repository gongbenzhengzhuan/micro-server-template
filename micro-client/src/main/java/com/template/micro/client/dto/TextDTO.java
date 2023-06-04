package com.template.micro.client.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@ApiModel(value = "TextDTO对象", description = "")
public class TextDTO implements Serializable {
   private String content_first;
    private String content_second;
}
