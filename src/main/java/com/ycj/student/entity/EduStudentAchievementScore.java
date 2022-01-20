package com.ycj.student.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName(value = "edu_student_achievement_score")
public class EduStudentAchievementScore {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer achievementId;
    private Integer courseId;
    private BigDecimal score;

}
