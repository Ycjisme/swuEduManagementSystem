package com.ycj.student.constants;

import lombok.Getter;

@Getter
public enum EnumAccountType {


    MANAGER(1, "管理员"),

    COMMON_USER(2, "普通用户");

    private Integer code;

    private String message;

    EnumAccountType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
