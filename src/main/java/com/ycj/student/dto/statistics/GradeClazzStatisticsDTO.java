package com.ycj.student.dto.statistics;

import lombok.Data;

/**
 * @author yangchengju
 */
@Data
public class GradeClazzStatisticsDTO {

    /**
     * 年级名称
     */
    private String gradeName;
    /**
     * 班级数
     */
    private int clazzNum;

}
