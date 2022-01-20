package com.ycj.student.exception;

import com.ycj.student.constants.EnumResponse;
import lombok.Data;

/**
 * @author yangchengju
 */
@Data
public class UsernameExistException extends Exception {

    private static final long serialVersionUID = 8507402093094958161L;

    private EnumResponse enumResponse;

    public UsernameExistException() {
        super();
    }

    public UsernameExistException(EnumResponse enumResponse){
        this.enumResponse = enumResponse;
    }

    public UsernameExistException(String message) {
        super(message);
    }


    public UsernameExistException(String message, Throwable cause) {
        super(message, cause);
    }


    public UsernameExistException(Throwable cause) {
        super(cause);
    }


    protected UsernameExistException(String message, Throwable cause,
                                     boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
