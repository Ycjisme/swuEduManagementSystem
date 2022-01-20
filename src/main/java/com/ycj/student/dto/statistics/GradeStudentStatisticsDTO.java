package com.ycj.student.dto.statistics;

import lombok.Data;

/**
 * @author yangchengju
 */
@Data
public class GradeStudentStatisticsDTO {

    /**
     * 年级名称
     */
    private String gradeName;
    /**
     * 学生个数
     */
    private Integer studentNum;

}
