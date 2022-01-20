package com.ycj.student.dto.clazz;

import com.ycj.student.dto.PageForm;
import lombok.Data;

import java.util.List;

@Data
public class ClazzSearchDTO extends PageForm {

    private String keyword;

    private List<Integer> clazzIdList;

}
