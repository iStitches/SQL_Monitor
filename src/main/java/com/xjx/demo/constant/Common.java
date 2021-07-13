package com.xjx.demo.constant;

import com.xjx.demo.exception.BaseErrorInfoInterface;

public enum Common implements BaseErrorInfoInterface {
    SUCCESS("200","成功"),
    INTERNAL_SERVER_ERROR("500","服务器内部错误！"),
    ERROR("400","请求失败！"),
    GOODS_NOT_FOUND("50001","商品信息不存在！"),
    MYBATIS_INVALID_BINDING("50002","MyBatis出现无效的信息绑定问题"),
    MYBATIS_INVALID_ARGUMENTS("50003","MyBatis出现参数无法匹配的问题"),
    MYBATIS_INVALID_RESULT("50004","MyBatis出现接收参数格式无法匹配的问题")
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
