package com.ycj.student.service.course;

import com.ycj.student.dto.course.CourseDTO;
import com.ycj.student.dto.course.CourseSearchDTO;

import java.util.List;

public interface CourseService {

    List<CourseDTO> getCourseList(CourseSearchDTO searchDTO, boolean isPage);

    void addCourse(CourseDTO courseDTO);

    void updateCourse(CourseDTO courseDTO);

    void deleteCourse(CourseDTO courseDTO);

}
