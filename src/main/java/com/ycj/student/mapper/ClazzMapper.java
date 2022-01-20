package com.ycj.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycj.student.dto.clazz.ClazzDTO;
import com.ycj.student.dto.clazz.ClazzSearchDTO;
import com.ycj.student.entity.EduClazz;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangchengju
 */
@Repository
public interface ClazzMapper extends BaseMapper<EduClazz> {

    List<ClazzDTO> listAll(ClazzSearchDTO searchDTO);

}
