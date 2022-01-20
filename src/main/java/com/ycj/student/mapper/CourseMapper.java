package com.ycj.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycj.student.dto.course.CourseDTO;
import com.ycj.student.dto.course.CourseSearchDTO;
import com.ycj.student.entity.EduCourse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseMapper extends BaseMapper<EduCourse> {


    List<CourseDTO> listCourse(CourseSearchDTO searchDTO);

}
