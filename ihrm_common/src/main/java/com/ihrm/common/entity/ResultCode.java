package com.ihrm.common.entity;

/**
 * @Author: 846602483
 * @Date: 2019-8-3 17:08
 * @Version 1.0
 * 返回码定义类
 */
public enum ResultCode {
    SUCCESS(true,10000,"操作成功！"),
    //------系统错误返回码------
    FAIL(false,10001,"操作失败!"),
    UNAUTHENTICATED(false,10002,"您好未登录!"),
    UNAUTHORISE(false,10003,"权限不足"),
    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试!"),
    MOBILEORPASSWORDERROR(false,10004,"账号或密码错误");


    //----用户操作返回码----
    //----企业操作返回码----
    //----权限操作返回码----
    //----其它操作返回码----


    //操作是否成功
    boolean success;

    //操作代码
    int code;

    //提示信息
    String message;

    ResultCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean success() {
        return success;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
