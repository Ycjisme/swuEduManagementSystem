package com.ycj.student.mapper;

import com.ycj.student.dto.statistics.ClazzStatisticsDTO;
import com.ycj.student.dto.statistics.GradeStatisticsDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticsMapper {

    List<ClazzStatisticsDTO> countClazzStudentNumber(@Param("department") String department);

    List<GradeStatisticsDTO> countGradeClazzNumber();

    Integer countGradeStudentNumber(@Param("clazzIdList") List<Integer> clazzIdList);

}
