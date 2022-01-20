package com.ycj.student.dto.account;

import com.ycj.student.dto.PageForm;
import lombok.Data;

@Data
public class AccountSearchDTO extends PageForm {

    private String username;
    private Integer status;

}
