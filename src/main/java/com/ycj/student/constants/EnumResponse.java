package com.ycj.student.constants;

import lombok.Getter;

@Getter
public enum EnumResponse {


    SUCCESS(200, "请求成功"),

    FAIL(201, "用户名和密码错误"),

    LOGIN_INVALID_STATUS(202, "当前用户已经被禁用"),

    NOT_EXIST_USER(203, "当前用户不存在！"),

    USER_TOKEN_TIMEOUT(204, "用户会话超时"),

    USERNAME_EXIST_EXCEPTION(205, "账号已存在"),

    OLD_PASSWORD_ERROR_EXCEPTION(206, "原密码不正确"),

    ID_CART_NO_EXIST_EXCEPTION(207, "身份证号已存在"),

    INVALID_PARAM(300, "参数有误"),

    SYSTEM_EXCEPTION(500,"系统异常，请联系管理员"),

    SOFTWARE_TIMEOUT_EXCEPTION(900,"");

    private Integer code;

    private String message;

    EnumResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
