package com.wangxin.springboot.common.constant;

import com.wangxin.springboot.common.enums.UserResultEnum;

public class UserResult<T> extends BaseResult{

    private T data;

    public UserResult(UserResultEnum userResultEnum, T data) {
        super(userResultEnum.getCode(), userResultEnum.getmsg(), data);
    }


}
