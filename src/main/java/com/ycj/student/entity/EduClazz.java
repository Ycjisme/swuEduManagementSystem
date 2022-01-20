package com.ycj.student.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "edu_clazz")
public class EduClazz {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String department;
    private Integer deleteFlag;
    private Date createTime;
    private Date updateTime;

}
