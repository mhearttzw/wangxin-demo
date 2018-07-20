package com.wangxin.springboot.common.constant;

public class BaseResult {

    /**
     * 状态码
     */
    public int code;

    /**
     * 状态码说明
     */
    public String message;

    /**
     * 返回结果
     */
    public Object data;

    public BaseResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
