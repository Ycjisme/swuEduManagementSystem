package com.ycj.student.exception;

import com.ycj.student.constants.EnumResponse;
import lombok.Data;

@Data
public class ModifyPasswordException extends Exception {


    private EnumResponse enumResponse;

    public ModifyPasswordException() {
        super();
    }

    public ModifyPasswordException(EnumResponse enumResponse){
        this.enumResponse = enumResponse;
    }

    public ModifyPasswordException(String message) {
        super(message);
    }


    public ModifyPasswordException(String message, Throwable cause) {
        super(message, cause);
    }


    public ModifyPasswordException(Throwable cause) {
        super(cause);
    }


    protected ModifyPasswordException(String message, Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
