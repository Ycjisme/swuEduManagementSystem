package com.ycj.student.service.statistics.impl;

import com.google.common.collect.Lists;
import com.ycj.student.dto.statistics.ClazzStatisticsDTO;
import com.ycj.student.dto.statistics.GradeClazzStatisticsDTO;
import com.ycj.student.dto.statistics.GradeStatisticsDTO;
import com.ycj.student.dto.statistics.GradeStudentStatisticsDTO;
import com.ycj.student.mapper.StatisticsMapper;
import com.ycj.student.service.statistics.StatisticsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private StatisticsMapper statisticsMapper;

    @Override
    public List<ClazzStatisticsDTO> countClazzStudentNumber(String department) {
        if(StringUtils.isEmpty(department)){
            return null;
        }
        return statisticsMapper.countClazzStudentNumber(department);
    }

    @Override
    public List<GradeStudentStatisticsDTO> countGradeStudentNumber() {

        List<GradeStatisticsDTO> gradeStatisticsDTOList = statisticsMapper.countGradeClazzNumber();

        if(CollectionUtils.isEmpty(gradeStatisticsDTOList)){
            return Lists.newArrayList();
        }


        List<GradeStudentStatisticsDTO> list = Lists.newArrayList();

        gradeStatisticsDTOList.forEach(item->{
            String clazzIds = item.getClazzIds();
            List<Integer> clazzIdList = Lists.newArrayList(clazzIds.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());

            if(CollectionUtils.isEmpty(clazzIdList)){
               return;
            }

            GradeStudentStatisticsDTO gradeStudentStatisticsDTO = new GradeStudentStatisticsDTO();
            gradeStudentStatisticsDTO.setGradeName(item.getGradeName());
            gradeStudentStatisticsDTO.setStudentNum(statisticsMapper.countGradeStudentNumber(clazzIdList));

            list.add(gradeStudentStatisticsDTO);
        });


        return list;
    }

    @Override
    public List<GradeClazzStatisticsDTO> countGradeClazzNumber() {

        List<GradeClazzStatisticsDTO> gradeClazzStatisticsDTOList = Lists.newArrayList();

        List<GradeStatisticsDTO> gradeStatisticsDTOList = statisticsMapper.countGradeClazzNumber();

        if(CollectionUtils.isEmpty(gradeStatisticsDTOList)){
            return Lists.newArrayList();
        }

        gradeStatisticsDTOList.forEach(item->{
            GradeClazzStatisticsDTO gradeClazzStatisticsDTO = new GradeClazzStatisticsDTO();
            gradeClazzStatisticsDTO.setGradeName(item.getGradeName());
            if(StringUtils.isEmpty(item.getClazzIds())){
                gradeClazzStatisticsDTO.setClazzNum(0);
            }else {
                gradeClazzStatisticsDTO.setClazzNum(item.getClazzIds().split(",").length);
            }
            gradeClazzStatisticsDTOList.add(gradeClazzStatisticsDTO);
        });


        return gradeClazzStatisticsDTOList;
    }
}
