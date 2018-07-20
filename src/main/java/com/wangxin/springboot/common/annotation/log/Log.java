package com.wangxin.springboot.common.annotation.log;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
@Documented
public @interface Log {
    String logStr() default "echelon";
}
