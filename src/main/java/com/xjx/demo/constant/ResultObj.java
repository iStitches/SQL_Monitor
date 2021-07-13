package com.xjx.demo.constant;

import com.xjx.demo.exception.BaseErrorInfoInterface;
import lombok.Data;

@Data
public class ResultObj {
    private String code;
    private String msg;
    private Object object;

    /**
     * 响应成功
     * @param code
     * @param msg
     * @param object
     * @return
     */
    public static ResultObj success(String code, String msg, Object object) {
        ResultObj res = new ResultObj();
        res.setCode(code);
        res.setMsg(msg);
        res.setObject(object);
        return res;
    }
    public static ResultObj success(){
        ResultObj resultObj = new ResultObj();
        resultObj.setCode(Common.SUCCESS.getResultCode());
        resultObj.setMsg(Common.SUCCESS.getResultMsg());
        resultObj.setObject(null);
        return resultObj;
    }
    public static ResultObj success(BaseErrorInfoInterface base){
        ResultObj resultObj = new ResultObj();
        resultObj.setCode(base.getResultCode());
        resultObj.setMsg(base.getResultMsg());
        resultObj.setObject(null);
        return resultObj;
    }
    public static ResultObj success(Object obj){
        ResultObj res = success();
        res.setObject(obj);
        return res;
    }

    public static ResultObj failure(String code, String msg, Object object){
        ResultObj res = new ResultObj();
        res.setCode(code);
        res.setMsg(msg);
        res.setObject(object);
        return res;
    }
    public static ResultObj failure(BaseErrorInfoInterface error){
        return failure(error.getResultCode(),error.getResultMsg(),null);
    }
}
