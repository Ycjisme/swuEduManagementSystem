package com.ycj.student.dto.achievement;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author yangchengju
 */
@Data
public class StudentAchievementDTO {

    private Integer id;
    private Integer studentId;
    private String studentName;
    private String clazzName;
    private String name;
    private BigDecimal totalScore;
    private Integer deleteFlag;
    private Date createTime;
    private Date updateTime;
    private List<StudentAchievementScoreDTO> scoreList;

}
