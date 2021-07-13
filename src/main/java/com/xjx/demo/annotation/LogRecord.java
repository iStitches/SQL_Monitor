package com.xjx.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogRecord {
    /**
     * 异常产生的类
     * @return
     */
    String location();

    /**
     * 异常产生的方法名称
     * @return
     */
    String methodName();
}
