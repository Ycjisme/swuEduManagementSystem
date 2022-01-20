package com.ycj.student.dto.statistics;

import lombok.Data;

/**
 * @author yangchengju
 */
@Data
public class ClazzStatisticsDTO {

    /**
     * 班级名称
     */
    private String clazzName;
    /**
     * 学生个数
     */
    private Integer studentNum;

}
