package com.xjx.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {
    @Pointcut("@annotation(com.xjx.demo.annotation.LogRecord)")
    public void logPointCut(){
    };

    @AfterThrowing(pointcut = "logPointCut()",throwing = "e")
    public void ErrorLog(Exception e, JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        int a = 2;
    }
}
