package com.ycj.student.dto.achievement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentAchievementScoreDTO {

    private Integer id;
    private Integer achievementId;
    private Integer courseId;
    private BigDecimal score;

}
