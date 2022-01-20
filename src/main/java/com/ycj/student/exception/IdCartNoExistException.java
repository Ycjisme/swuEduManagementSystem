package com.ycj.student.exception;

import com.ycj.student.constants.EnumResponse;
import lombok.Data;

@Data
public class IdCartNoExistException extends Exception {


    private EnumResponse enumResponse;

    public IdCartNoExistException() {
        super();
    }

    public IdCartNoExistException(EnumResponse enumResponse){
        this.enumResponse = enumResponse;
    }

    public IdCartNoExistException(String message) {
        super(message);
    }


    public IdCartNoExistException(String message, Throwable cause) {
        super(message, cause);
    }


    public IdCartNoExistException(Throwable cause) {
        super(cause);
    }


    protected IdCartNoExistException(String message, Throwable cause,
                                     boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
