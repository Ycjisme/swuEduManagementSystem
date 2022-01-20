package com.ycj.student.service.course.impl;

import com.github.pagehelper.PageHelper;
import com.ycj.student.constants.EnumDeleteFlag;
import com.ycj.student.dto.course.CourseDTO;
import com.ycj.student.dto.course.CourseSearchDTO;
import com.ycj.student.entity.EduCourse;
import com.ycj.student.mapper.CourseMapper;
import com.ycj.student.service.course.CourseService;
import com.ycj.student.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
@Slf4j
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseMapper courseMapper;
    @Resource
    private MapperFacade mapperFacade;

    @Override
    public List<CourseDTO> getCourseList(CourseSearchDTO searchDTO, boolean isPage) {
        if (isPage) {
            PageHelper.startPage(searchDTO.getPageNum(), searchDTO.getPageSize());
        }
        return courseMapper.listCourse(searchDTO);
    }

    @Override
    public void addCourse(CourseDTO courseDTO) {

        EduCourse eduCourse = mapperFacade.map(courseDTO, EduCourse.class);

        eduCourse.setCreateTime(DateUtils.getCurrentDate());
        eduCourse.setDeleteFlag(EnumDeleteFlag.NO.getCode());

        courseMapper.insert(eduCourse);
        log.info("课程插入成功");
    }

    @Override
    public void updateCourse(CourseDTO courseDTO) {

        if (null == courseDTO.getId()) {
            return;
        }

        EduCourse eduCourse = mapperFacade.map(courseDTO, EduCourse.class);
        eduCourse.setUpdateTime(DateUtils.getCurrentDate());

        courseMapper.updateById(eduCourse);
        log.info("课程更新成功");

    }

    /**
     * 将删除标志为设为1
     * @param courseDTO
     */
    @Override
    public void deleteCourse(CourseDTO courseDTO) {

        if(courseDTO.getId() == null){
            return;
        }

        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseDTO.getId());
        eduCourse.setDeleteFlag(EnumDeleteFlag.YES.getCode());

        courseMapper.updateById(eduCourse);
        log.info("课程删除成功");

    }
}
