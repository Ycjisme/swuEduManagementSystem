package com.ycj.student.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "edu_course")
public class EduCourse {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String teacher;
    private String remark;
    private Integer deleteFlag;
    private Date createTime;
    private Date updateTime;

}
