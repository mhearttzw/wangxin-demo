package com.wangxin.springboot.common.constant;

/**
 * @created_time    2018/7/17
 * @author          echelon
 * @description     系统接口结果常量返回类
 */
public enum UserResultConstant {
    /**
     * 成功
     */
    SUCCESS(10001, "success"),
    PRODUCT_EXISTS(10002, "用户已存在！"),


    /**
     * 失败
     */
    FAILED(10101, "failed"),
    PRODUCT_NOT_EXISTS(10102, "用户不存在！")
    ;


    public int code;

    public String message;

    UserResultConstant(int code, String message) {
        this.code = code;
        this.message = message;
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
}