package com.xjx.demo.constant;

import com.xjx.demo.exception.BaseErrorInfoInterface;

public enum Common implements BaseErrorInfoInterface {
    SUCCESS("200","成功"),
    INTERNAL_SERVER_ERROR("500","服务器内部错误！"),
    ERROR("400","请求失败！"),
    GOODS_NOT_FOUND("50001","商品信息不存在！")
    ;

    private String code;
    private String msg;

    Common(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getResultCode() {
        return this.code;
    }

    @Override
    public String getResultMsg() {
        return this.msg;
    }
}
