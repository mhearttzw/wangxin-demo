package com.wangxin.springboot.controller.handler;

import com.wangxin.springboot.common.constant.BaseResult;
import com.wangxin.springboot.common.constant.UserResult;
import com.wangxin.springboot.common.enums.UserResultEnum;
import com.wangxin.springboot.common.exception.ProductException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandlerImp {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandlerImp.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResult handle(Exception e) {
        if (e instanceof ProductException) {
            ProductException productException = (ProductException) e;
            return new UserResult<>(productException.getUserResultEnum(), "");
        } else {
            logger.error("[系统异常】{}", e);
            return new UserResult<>(UserResultEnum.FAILED, "");
        }
    }

}
