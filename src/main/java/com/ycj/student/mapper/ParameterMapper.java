package com.ycj.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycj.student.dto.parameter.ParameterDTO;
import com.ycj.student.entity.EduParameter;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParameterMapper extends BaseMapper<EduParameter> {

    List<ParameterDTO> list(@Param("parameterType") String parameterType);

}
