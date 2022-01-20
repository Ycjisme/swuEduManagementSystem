package com.ycj.student.dto.clazz;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ClazzDTO implements Serializable {

    private static final long serialVersionUID = -7077403507954877158L;
    private Integer id;
    private String name;
    private String department;
    private String allName;
    private Integer deleteFlag;
    private Date createTime;
    private Date updateTime;

}
