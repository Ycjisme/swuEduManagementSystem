package com.ycj.student.dto.parameter;

import lombok.Data;

import java.io.Serializable;

@Data
public class ParameterDTO implements Serializable{

    private static final long serialVersionUID = -6278526025858424283L;

    private Integer id;
    private String parameterType;
    private String parameterKey;
    private String parameterValue;
    private Integer deleteFlag;

}
