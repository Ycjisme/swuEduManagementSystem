package com.ycj.student.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "edu_parameter")
public class EduParameter {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String parameterType;
    private String parameterKey;
    private String parameterValue;
    private Integer deleteFlag;
    private Date createTime;

}
