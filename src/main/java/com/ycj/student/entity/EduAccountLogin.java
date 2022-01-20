package com.ycj.student.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "edu_account_login")
public class EduAccountLogin {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String token;
    private String username;
    private String userInfo;
    private Date createTime;

}
