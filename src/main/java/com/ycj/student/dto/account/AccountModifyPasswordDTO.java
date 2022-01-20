package com.ycj.student.dto.account;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountModifyPasswordDTO implements Serializable {


    private static final long serialVersionUID = 4766725578010783832L;
    private Integer id;
    private String oldPassword;
    private String newPassword;

}
