package com.ycj.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycj.student.dto.achievement.StudentAchievementScoreDTO;
import com.ycj.student.entity.EduStudentAchievementScore;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangchengju
 */
@Repository
public interface StudentAchievementScoreMapper extends BaseMapper<EduStudentAchievementScore> {

    void deleteByAchievementId(Integer achievementId);

    List<StudentAchievementScoreDTO> getScoreListByAchievementId(Integer achievementId);

}
