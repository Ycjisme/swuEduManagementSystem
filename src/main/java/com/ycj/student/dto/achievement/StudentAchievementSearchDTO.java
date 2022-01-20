package com.ycj.student.dto.achievement;

import com.ycj.student.dto.PageForm;
import lombok.Data;

import java.util.List;

/**
 * @author yangchengju
 */
@Data
public class StudentAchievementSearchDTO  extends PageForm {

    private String keyword;
    private List<Integer> clazzIdList;

}
