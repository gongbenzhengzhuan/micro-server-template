package utils.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 统一接口返回参数封装类
 *
 * @author lizhongpeng 2017/09/29 09:38
 */
@Slf4j
@ApiModel(value = "响应参数", description = "响应参数")
public class Result<T> implements Serializable {

    private static final String OK = "操作成功";
    private static final String ERROR = "error";
    //正常响应码
    public static final Integer CODE_200 = 200;
    //无权限(认证无效)响应码
    public static final Integer CODE_201 = 201;
    //无权限访问响应码
    public static final Integer CODE_202 = 202;
    //参数错误响应码
    public static final Integer CODE_INVALID_PARAMS = 203;
    //token失效
    public static final Integer CODE_EXPIRED_TOKEN = 204;
    //400错误响应码
    public static final Integer CODE_400 = 400;
    public static final Integer CODE_403 = 403;
    public static final Integer CODE_404 = 404;
    public static final Integer CODE_500 = 500;

    @ApiModelProperty(value = "返回元数据信息")
    private Meta meta;
    @ApiModelProperty(value = "返回数据")
    private T data;
    @ApiModelProperty(value = "状态码")
    private Integer code;
    @ApiModelProperty(value = "总数")
    private Integer total = 0;//总数

    /**
     * 赋值列表查询总记录数
     *
     * @param total
     * @return
     */
    public Result<T> total(int total) {
        this.total = total;
        return this;
    }

    public Result<T> success(T data) {
        this.meta = new Meta(true, OK);
        this.data = data;
        this.code = CODE_200;
        return this;
    }

    public Result<T> msg(String message) {
        this.meta = new Meta(true, message);
        this.data = null;
        this.code = CODE_200;
        return this;
    }

    public Result<T> failure() {
        this.meta = new Meta(false, ERROR);
        this.data = null;
        this.code = CODE_400;
        return this;
    }

    public Result<T> code(Integer code) {
        this.code = code;
        return this;
    }

    public Result<T> failure(String message) {
        this.meta = new Meta(false, message);
        this.data = null;
        this.code = CODE_400;
        return this;
    }

    public Meta getMeta() {
        return meta;
    }

    public T getData() {
        return data;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * 输出json格式
     *
     * @return
     */
//    public String toJson() {
//        return UtilsApi.formatJson(UtilsApi.toJson(this));
//    }

    public static class Meta implements Serializable {

        private boolean success;
        private String message;

        public Meta() {
        }

        public Meta(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
