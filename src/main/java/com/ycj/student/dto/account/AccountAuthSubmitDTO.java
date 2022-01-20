package com.ycj.student.dto.account;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yangchengju
 */
@Data
public class AccountAuthSubmitDTO implements Serializable {

    private static final long serialVersionUID = -6914799033916932935L;
    /**
     * 此id为account_id
     */
    private Integer id;

    private List<Integer> clazzIdList;

}
