package com.ycj.student.vo;

import lombok.Data;

/**
 * @author yangchengju
 */
@Data
public class StudentFileVo {

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件的二进制数组
     */
    private byte[] fileBytes;
}
