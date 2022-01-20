package com.ycj.student.dto.student;

import com.ycj.student.dto.PageForm;
import lombok.Data;

import java.util.List;

/**
 * @author yangchengju
 */
@Data
public class StudentSearchDTO extends PageForm {

    private Long studentId;
    private String keyword;
    private String department;
    private String clazzName;
    private Integer deleteFlag;
    private Integer displayUserNo;
    private List<Integer> clazzIdList;

}
