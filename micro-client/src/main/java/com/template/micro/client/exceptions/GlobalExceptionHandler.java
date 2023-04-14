package com.template.micro.client.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import utils.utils.UtilsApi;
import utils.vo.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常收集
 * @authoResult lizhongpeng 2018/10/18 17:19
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 全局异常捕获
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result errorHandler(Exception ex) {
        log.error("GlobalException:\r\n{}", ex);
        return new Result().failure("未查询到有效数据,请稍后重试!");
    }

    /**
     * 自定义异常捕获
     * @param ex
     * @return
     */
    @ExceptionHandler(value = CustomException.class)
    @ResponseBody
    public Result myErrorHandler(CustomException ex) {
        return new Result().failure(ex.getMsg());
    }

    /***
     * 404处理
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFountHandler(HttpServletRequest request, NoHandlerFoundException e){
        String method = request.getMethod();
        String path = request.getRequestURI();
        Map<String,Object> data = new HashMap<>();
        data.put("method",method);
        data.put("path",path);
        return "redirect:";
    }

    /**
     * 参数有效性异常拦截
     * @authoResult lizhongpeng
     * @date 2021/11/05 15:11
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result bindExceptionHandler(MethodArgumentNotValidException e) {
        if(UtilsApi.isNotNull(e.toString())){
            return new Result().failure(e.getBindingResult().getFieldError().getDefaultMessage());
        }
        return new Result().failure("未查询到有效数据,请稍后重试!");
    }
}
