package com.ycj.student.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "edu_student_achievement")
public class EduStudentAchievement {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer studentId;
    private String name;
    private BigDecimal totalScore;
    private Integer deleteFlag;
    private Date createTime;
    private Date updateTime;

}
