package com.ycj.student.dto.student;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yangchengju
 */
@Data
public class StudentInfoExcelSheetDTO implements Serializable {

    private static final long serialVersionUID = -418903160146935023L;

    private String name;

    private String userNo;

    private Integer age;

    private String identityCardNo;

    private Integer sex;

    private String sexStr;

    private String guardian;

    private String mobile;

    private String studentNo;
    //班级
    private String clazzName;
    //年纪
    private String department;

    private String province;

    private String city;

    private String address;

    private String allAddress;

    private String remark;
}
