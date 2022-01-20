package com.ycj.student.exception;

import com.ycj.student.constants.EnumResponse;
import lombok.Data;

@Data
public class LoginException extends Exception {


    private EnumResponse enumResponse;

    public LoginException() {
        super();
    }

    public LoginException(EnumResponse enumResponse){
        this.enumResponse = enumResponse;
    }

    public LoginException(String message) {
        super(message);
    }


    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }


    public LoginException(Throwable cause) {
        super(cause);
    }


    protected LoginException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
