package com.ycj.student.dto;

import lombok.Data;

/**
 * @author yangchengju
 */
@Data
public class PageForm {

    private int pageNum = 1;

    private int pageSize = 10;

}
