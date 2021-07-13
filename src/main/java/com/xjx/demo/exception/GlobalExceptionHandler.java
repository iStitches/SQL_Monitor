package com.xjx.demo.exception;

import com.xjx.demo.constant.Common;
import com.xjx.demo.constant.ResultObj;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.BindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BindingException.class)
    public ResultObj dealWithBindingException(BindingException exception){
        log.error("========MyBatis出现了映射错误=======：{}",exception.getMessage());
        // 日志记录....
        return ResultObj.failure(Common.MYBATIS_INVALID_BINDING);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResultObj dealWithIllegalArgumentException(IllegalArgumentException exception){
        log.error("========MyBatis出现了字段名无法匹配的错误=======：{}",exception.getMessage());
        // 日志记录....
        return ResultObj.failure(Common.MYBATIS_INVALID_ARGUMENTS);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResultObj dealWithIllegalStateException(IllegalStateException exception){
        log.error("========MyBatis出现了结果格式不匹配的错误=======：{}",exception.getMessage());
        // 日志记录....
        return ResultObj.failure(Common.MYBATIS_INVALID_RESULT);
    }
}
