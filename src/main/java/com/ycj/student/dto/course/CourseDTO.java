package com.ycj.student.dto.course;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseDTO implements Serializable {

    private static final long serialVersionUID = -1272658897258878429L;

    private Integer id;
    private String name;
    private String teacher;
    private String remark;

}
