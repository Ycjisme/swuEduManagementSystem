package com.ycj.student.constants;

import lombok.Getter;

@Getter
public enum EnumStatus {


    ACTIVE(1, "有效"),

    INACTIVE(0, "无效");

    private Integer code;

    private String message;

    EnumStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
