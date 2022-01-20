package com.ycj.student.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "edu_student")
public class EduStudent {

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;
    private Integer clazzId;
    private String name;
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
    private Date deleteTime;
    private Integer createUserId;
    private Date createTime;
    private Date updateTime;

}
