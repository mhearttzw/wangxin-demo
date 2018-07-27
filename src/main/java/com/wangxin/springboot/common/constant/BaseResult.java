package com.wangxin.springboot.common.constant;

public class BaseResult {

    /**
     * 状态码
     */
    public int code;

    /**
     * 状态码说明
     */
    public String msg;

    /**
     * 返回结果
     */
    public Object data;

    public BaseResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getmsg() {
        return msg;
    }

    public void setmsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
