package com.ycj.student.service.clazz;

import com.ycj.student.dto.clazz.ClazzDTO;
import com.ycj.student.dto.clazz.ClazzSearchDTO;

import java.util.List;

/**
 * @author yangchengju
 */
public interface ClazzService {

    void addClazz(ClazzDTO classDTO);

    void updateClazz(ClazzDTO classDTO);

    void deleteClazz(Integer id);

    List<ClazzDTO> getClazzList(ClazzSearchDTO searchDTO,boolean isPage);

}
