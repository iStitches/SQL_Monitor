package com.xjx.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * MyBatis 执行切面
 */
@Aspect
@Component
@Slf4j
public class SqlErrorAspect {
    // 定义切点
    @Pointcut("@annotation(com.xjx.demo.annotation.SqlError)")
    public void sqlPointCut(){

    };

    /**
     * 异常切面
     * @param exception
     */
    @AfterThrowing(value = "sqlPointCut()", throwing = "exception")
    public void recordSqlError(JoinPoint joinPoint, Exception exception){
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
    }
}
