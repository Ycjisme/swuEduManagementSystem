package com.ycj.student.controller.statistics;

import com.ycj.student.constants.EnumResponse;
import com.ycj.student.service.statistics.StatisticsService;
import com.ycj.student.utils.Result;
import com.ycj.student.utils.ResultUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yangchengju
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;

    /**
     * 班级学生人数
     */
    @RequestMapping("/class/student/number")
    public Result countClassStudentNumbers(String department) {
        if (StringUtils.isEmpty(department)) {
            return ResultUtils.fail(EnumResponse.INVALID_PARAM);
        }
        return ResultUtils.success(statisticsService.countClazzStudentNumber(department));
    }

    /**
     * 年级学生人数
     */
    @RequestMapping("/grade/student/number")
    public Result countGradeStudentNumbers(){
        return ResultUtils.success(statisticsService.countGradeStudentNumber());
    }

    /**
     * 统计班级数
     */
    @RequestMapping("/grade/class/number")
    public Result countClassNumbers() {
        return ResultUtils.success(statisticsService.countGradeClazzNumber());
    }

}
