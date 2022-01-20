package com.ycj.student.service.clazz.impl;

import com.github.pagehelper.PageHelper;
import com.ycj.student.constants.EnumDeleteFlag;
import com.ycj.student.dto.clazz.ClazzDTO;
import com.ycj.student.dto.clazz.ClazzSearchDTO;
import com.ycj.student.entity.EduClazz;
import com.ycj.student.mapper.ClazzMapper;
import com.ycj.student.service.clazz.ClazzService;
import com.ycj.student.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yangchengju
 */
@Service
@Transactional
@Slf4j
public class ClazzServiceImpl implements ClazzService {

    @Resource
    private ClazzMapper clazzMapper;
    @Resource
    private MapperFacade mapperFacade;

    @Override
    public void addClazz(ClazzDTO classDTO) {

        if (null == classDTO) {
            return;
        }

        classDTO.setDeleteFlag(EnumDeleteFlag.NO.getCode());
        classDTO.setCreateTime(DateUtils.getCurrentDate());
        classDTO.setUpdateTime(DateUtils.getCurrentDate());

        EduClazz eduClazz = mapperFacade.map(classDTO, EduClazz.class);

        clazzMapper.insert(eduClazz);
        log.info("班级插入成功");

    }

    @Override
    public void updateClazz(ClazzDTO clazzDTO) {

        if (clazzDTO == null || clazzDTO.getId() == null) {
            return;
        }

        clazzDTO.setUpdateTime(DateUtils.getCurrentDate());

        EduClazz eduClazz = mapperFacade.map(clazzDTO, EduClazz.class);

        clazzMapper.updateById(eduClazz);
        log.info("班级更新成功");

    }

    /**
     * 删除班级是将删除标志位设置为1
     * @param id
     */
    @Override
    public void deleteClazz(Integer id) {

        ClazzDTO clazzDTO = new ClazzDTO();

        clazzDTO.setId(id);
        clazzDTO.setDeleteFlag(EnumDeleteFlag.YES.getCode());

        updateClazz(clazzDTO);

    }

    @Override
    public List<ClazzDTO> getClazzList(ClazzSearchDTO searchDTO, boolean isPage) {

        if(isPage){
            PageHelper.startPage(searchDTO.getPageNum(),searchDTO.getPageSize());
        }

        return clazzMapper.listAll(searchDTO);
    }
}
