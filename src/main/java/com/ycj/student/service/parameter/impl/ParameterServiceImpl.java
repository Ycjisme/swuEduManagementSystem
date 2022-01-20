package com.ycj.student.service.parameter.impl;

import com.ycj.student.dto.parameter.ParameterDTO;
import com.ycj.student.mapper.ParameterMapper;
import com.ycj.student.service.parameter.ParameterService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ParameterServiceImpl implements ParameterService {

    @Resource
    private ParameterMapper parameterMapper;

    @Override
    public List<ParameterDTO> list(String parameterType) {

        if (StringUtils.isEmpty(parameterType)) {
            return null;
        }

        return parameterMapper.list(parameterType);
    }
}
