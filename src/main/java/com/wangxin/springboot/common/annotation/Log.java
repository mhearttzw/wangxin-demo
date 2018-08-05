package com.wangxin.springboot.common.annotation;

import java.lang.annotation.*;

// @Retention表示这个注解要保留到什么时候， 可以只在源码中， 或者class 文件中， 或者是运行时
@Retention(RetentionPolicy.RUNTIME)
// @Target表示该注解的应用目标，可以是类、方法、 方法参数等等
@Target({ElementType.METHOD})
@Inherited
@Documented
public @interface Log {
    /**
     * 说明信息
     * @return
     */
    String logStr() default "echelon";

    /**
     * 是否忽略
     * @return
     */
    boolean ignore() default false;
}
