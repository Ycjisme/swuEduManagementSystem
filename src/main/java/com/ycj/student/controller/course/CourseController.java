package com.ycj.student.controller.course;

import com.github.pagehelper.PageInfo;
import com.ycj.student.dto.course.CourseDTO;
import com.ycj.student.dto.course.CourseSearchDTO;
import com.ycj.student.service.course.CourseService;
import com.ycj.student.utils.Result;
import com.ycj.student.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yangchengju
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    @RequestMapping("/list")
    public Result getCourseList(@RequestBody CourseSearchDTO searchDTO) {

        List<CourseDTO> courseList = courseService.getCourseList(searchDTO,true);

        return ResultUtils.success(new PageInfo<>(courseList));
    }

    @RequestMapping("/add")
    public Result add(@RequestBody CourseDTO courseDTO) {

        courseService.addCourse(courseDTO);

        return ResultUtils.success();
    }

    @RequestMapping("/update")
    public Result update(@RequestBody CourseDTO courseDTO) {

        courseService.updateCourse(courseDTO);

        return ResultUtils.success();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestBody CourseDTO courseDTO) {


        courseService.deleteCourse(courseDTO);

        return ResultUtils.success();
    }

}
