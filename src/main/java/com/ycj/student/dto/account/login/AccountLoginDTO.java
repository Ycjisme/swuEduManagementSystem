package com.ycj.student.dto.account.login;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AccountLoginDTO implements Serializable {

    private static final long serialVersionUID = 5585127311591717425L;

    private Integer id;
    private String token;
    private String userInfo;
    private Date createTime;

}
