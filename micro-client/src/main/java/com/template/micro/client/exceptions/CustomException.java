package com.template.micro.client.exceptions;

/**
 * 全局异常定义
 *
 * @author lizhongpeng 2018/10/18 17:11
 */
public class CustomException extends RuntimeException {

    public CustomException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
