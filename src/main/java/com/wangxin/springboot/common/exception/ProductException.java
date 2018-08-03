package com.wangxin.springboot.common.exception;

import com.wangxin.springboot.common.enums.UserResultEnum;

/**
 * @author          echelon
 * @email           2954632969@qq.com
 * @created_time    2018/8/2 11:42
 * @description     统一异常处理
 */
public class ProductException extends Exception {

    private int code;

    private UserResultEnum userResultEnum;

    public ProductException(UserResultEnum userResultEnum) {
        super(userResultEnum.getmsg());
        this.code = userResultEnum.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UserResultEnum getUserResultEnum() {
        return userResultEnum;
    }

    public void setUserResultEnum(UserResultEnum userResultEnum) {
        this.userResultEnum = userResultEnum;
    }
}
