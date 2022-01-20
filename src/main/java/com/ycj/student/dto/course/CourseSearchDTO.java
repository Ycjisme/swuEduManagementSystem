package com.ycj.student.dto.course;

import com.ycj.student.dto.PageForm;
import lombok.Data;

import java.io.Serializable;

@Data
public class CourseSearchDTO extends PageForm implements Serializable {

    private String keyword;
    private String name;
    private String teacher;

}
