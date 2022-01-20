package com.ycj.student.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yangchengju
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDeleteDTO implements Serializable {

    private static final long serialVersionUID = 4427203682045266439L;

    private Long id;
    private Integer deleteUserId;
    private String remark;

}
