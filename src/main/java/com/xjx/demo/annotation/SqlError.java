package com.xjx.demo.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SqlError {
    /**
     * 方法全名(包名+位置名)
     * @return
     */
    String methodName();

    /**
     * 目标方法实现的功能
     * @return
     */
    String description();
}
