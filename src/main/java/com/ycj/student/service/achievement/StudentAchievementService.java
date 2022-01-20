package com.ycj.student.service.achievement;

import com.ycj.student.dto.achievement.StudentAchievementDTO;
import com.ycj.student.dto.achievement.StudentAchievementSearchDTO;

import java.util.List;

/**
 * @author yangchengju
 */
public interface StudentAchievementService {

    List<StudentAchievementDTO> getAchievementsList(StudentAchievementSearchDTO searchDTO,boolean isPage);

    void addAchievement(StudentAchievementDTO studentAchievementDTO);

    void update(StudentAchievementDTO studentAchievementDTO);
}
