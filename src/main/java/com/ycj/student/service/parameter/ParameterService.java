package com.ycj.student.service.parameter;

import com.ycj.student.dto.parameter.ParameterDTO;

import java.util.List;

public interface ParameterService {

    List<ParameterDTO> list(String parameterType);

}
