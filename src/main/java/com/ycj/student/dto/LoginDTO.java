package com.ycj.student.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yangchengju
 */
@Data
public class LoginDTO implements Serializable {
    private static final long serialVersionUID = 2410361020093049613L;

    private String username;
    private String password;

}
