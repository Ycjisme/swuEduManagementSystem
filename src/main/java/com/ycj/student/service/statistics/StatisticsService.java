package com.ycj.student.service.statistics;

import com.ycj.student.dto.statistics.ClazzStatisticsDTO;
import com.ycj.student.dto.statistics.GradeClazzStatisticsDTO;
import com.ycj.student.dto.statistics.GradeStudentStatisticsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yangchengju
 */
public interface StatisticsService {

    List<ClazzStatisticsDTO> countClazzStudentNumber(@Param("department") String department);

    List<GradeStudentStatisticsDTO> countGradeStudentNumber();

    List<GradeClazzStatisticsDTO> countGradeClazzNumber();
}
