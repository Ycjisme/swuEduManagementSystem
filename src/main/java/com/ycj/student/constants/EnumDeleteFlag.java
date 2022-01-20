package com.ycj.student.constants;

import lombok.Getter;

@Getter
public enum EnumDeleteFlag {


    YES(1, "已删除"),

    NO(0, "未删除");

    private Integer code;

    private String message;

    EnumDeleteFlag(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
