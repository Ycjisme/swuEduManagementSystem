package com.ycj.student.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yangchengju
 */
@Data
public class LogoutDTO implements Serializable {
    private static final long serialVersionUID = -1808577264298918545L;
    private String username;
}
