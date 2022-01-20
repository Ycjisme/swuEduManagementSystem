package com.ycj.student.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yangchengju
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO implements Serializable {

    private static final long serialVersionUID = 9208328631702951709L;

    private Long id;
    private Integer clazzId;
    private String clazzName;
    private String department;
    private String name;
    private String userNo;
    private Integer age;
    private Integer sex;
    private String identityCardNo;
    private String mobile;
    private String guardian;
    private String studentNo;
    private String province;
    private String city;
    private String address;
    private String remark;
    private Integer deleteFlag;
    private Integer deleteUserId;
    private String deleteUsername;
    private Date deleteTime;
    private Integer createUserId;
    private String createUsername;
    private Date createTime;
    private Date updateTime;

}
