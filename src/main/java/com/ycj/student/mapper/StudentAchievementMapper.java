package com.ycj.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycj.student.dto.achievement.StudentAchievementDTO;
import com.ycj.student.dto.achievement.StudentAchievementSearchDTO;
import com.ycj.student.entity.EduStudentAchievement;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yangchengju
 */
@Repository
public interface StudentAchievementMapper extends BaseMapper<EduStudentAchievement> {

    List<StudentAchievementDTO> getAchievementsList(StudentAchievementSearchDTO searchDTO);

}
