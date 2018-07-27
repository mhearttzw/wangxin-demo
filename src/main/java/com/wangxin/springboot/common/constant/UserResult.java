package com.wangxin.springboot.common.constant;

public class UserResult<T> extends BaseResult{

    private T data;

    public UserResult(UserResultConstant userResultConstant, T data) {
        super(userResultConstant.getCode(), userResultConstant.getmsg(), data);
    }

}
