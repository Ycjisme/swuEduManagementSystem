package com.ycj.student.dto.account.auth;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yangchengju
 */
@Data
public class AccountAuthDTO implements Serializable {
    private static final long serialVersionUID = 1782584623269773637L;

    private Integer id;
    private Integer accountId;
    private Integer clazzId;
    private String clazzName;
    private String department;

}
