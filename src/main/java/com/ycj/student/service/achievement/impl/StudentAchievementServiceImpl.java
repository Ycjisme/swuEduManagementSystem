package com.ycj.student.service.achievement.impl;

import com.github.pagehelper.PageHelper;
import com.ycj.student.constants.EnumDeleteFlag;
import com.ycj.student.dto.achievement.StudentAchievementDTO;
import com.ycj.student.dto.achievement.StudentAchievementScoreDTO;
import com.ycj.student.dto.achievement.StudentAchievementSearchDTO;
import com.ycj.student.entity.EduStudentAchievement;
import com.ycj.student.entity.EduStudentAchievementScore;
import com.ycj.student.mapper.StudentAchievementMapper;
import com.ycj.student.mapper.StudentAchievementScoreMapper;
import com.ycj.student.service.achievement.StudentAchievementService;
import com.ycj.student.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yangchengju
 */
@Service
@Transactional
@Slf4j
public class StudentAchievementServiceImpl implements StudentAchievementService {

    @Resource
    private StudentAchievementMapper studentAchievementMapper;
    @Resource
    private StudentAchievementScoreMapper studentAchievementScoreMapper;
    @Resource
    private MapperFacade mapperFacade;

    @Override
    public List<StudentAchievementDTO> getAchievementsList(StudentAchievementSearchDTO searchDTO, boolean isPage) {

        if (isPage) {
            PageHelper.startPage(searchDTO.getPageNum(), searchDTO.getPageSize());
        }

        List<StudentAchievementDTO> achievementDTOList = studentAchievementMapper.getAchievementsList(searchDTO);

        for (StudentAchievementDTO achievementDTO : achievementDTOList) {

            achievementDTO.setScoreList(studentAchievementScoreMapper.getScoreListByAchievementId(achievementDTO.getId()));

        }
        log.info("成绩列表为：{}", achievementDTOList);

        return achievementDTOList;
    }

    @Override
    public void addAchievement(StudentAchievementDTO studentAchievementDTO) {

        studentAchievementDTO.setCreateTime(DateUtils.getCurrentDate());
        studentAchievementDTO.setUpdateTime(DateUtils.getCurrentDate());
        studentAchievementDTO.setDeleteFlag(EnumDeleteFlag.NO.getCode());

        EduStudentAchievement eduStudentAchievement = mapperFacade.map(studentAchievementDTO, EduStudentAchievement.class);
        eduStudentAchievement.setTotalScore(getTotalScore(studentAchievementDTO));

        studentAchievementMapper.insert(eduStudentAchievement);
        log.info("插入成绩总分成功");

        studentAchievementDTO.setId(eduStudentAchievement.getId());

        handleStudentAchievementScore(studentAchievementDTO);
    }

    @Override
    public void update(StudentAchievementDTO studentAchievementDTO) {

        if (studentAchievementDTO.getId() == null) {
            return;
        }

        EduStudentAchievement eduStudentAchievement = mapperFacade.map(studentAchievementDTO, EduStudentAchievement.class);

        eduStudentAchievement.setTotalScore(getTotalScore(studentAchievementDTO));

        studentAchievementMapper.updateById(eduStudentAchievement);
        log.info("更新成绩总分成功");

        handleStudentAchievementScore(studentAchievementDTO);
    }

    /**
     * 记录各个课程成绩
     * @param studentAchievementDTO
     */
    private void handleStudentAchievementScore(StudentAchievementDTO studentAchievementDTO) {

        studentAchievementScoreMapper.deleteByAchievementId(studentAchievementDTO.getId());


        List<StudentAchievementScoreDTO> scoreList = studentAchievementDTO.getScoreList();

        if (CollectionUtils.isEmpty(scoreList)) {
            return;
        }

        for (StudentAchievementScoreDTO scoreDTO : scoreList) {
            EduStudentAchievementScore score = new EduStudentAchievementScore();
            score.setCourseId(scoreDTO.getCourseId());
            score.setScore(scoreDTO.getScore());
            score.setAchievementId(studentAchievementDTO.getId());
            studentAchievementScoreMapper.insert(score);
        }
    }

    private BigDecimal getTotalScore(StudentAchievementDTO studentAchievementDTO) {

        List<StudentAchievementScoreDTO> scoreList = studentAchievementDTO.getScoreList();

        if (CollectionUtils.isEmpty(scoreList)) {
            return new BigDecimal(0);
        }

        return scoreList.stream().map(StudentAchievementScoreDTO::getScore).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
