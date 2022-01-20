package com.ycj.student.dto.account;

import com.ycj.student.dto.account.auth.AccountAuthDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class AccountDTO implements Serializable {

    private static final long serialVersionUID = -3109017560291245175L;

    private Integer id;
    private String username;
    private String password;
    private Integer status;
    private Integer accountType;
    private Date createTime;
    private Date lastLoginTime;
    private List<AccountAuthDTO> authList;

}
