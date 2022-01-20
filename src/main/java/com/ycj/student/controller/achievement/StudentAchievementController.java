package com.ycj.student.controller.achievement;

import com.github.pagehelper.PageInfo;
import com.ycj.student.constants.EnumDeleteFlag;
import com.ycj.student.constants.EnumResponse;
import com.ycj.student.context.SessionContext;
import com.ycj.student.dto.achievement.StudentAchievementDTO;
import com.ycj.student.dto.achievement.StudentAchievementSearchDTO;
import com.ycj.student.service.account.auth.AccountAuthService;
import com.ycj.student.service.achievement.StudentAchievementService;
import com.ycj.student.utils.Result;
import com.ycj.student.utils.ResultUtils;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yangchengju
 */
@RestController
@RequestMapping("/student/achievement")
public class StudentAchievementController {

    @Resource
    private StudentAchievementService studentAchievementService;
    @Resource
    private AccountAuthService accountAuthService;

    /**
     * 显示成绩列表
     * @param searchDTO：如果keyWord不为空则根据关键字查询；为空则展示全部
     * @return
     */
    @RequestMapping("/list")
    public Result list(@RequestBody StudentAchievementSearchDTO searchDTO) {

        searchDTO.setClazzIdList(accountAuthService.queryClazzIdByAccountId(SessionContext.getAccountId()));

        List<StudentAchievementDTO> studentAchievementDTOList = studentAchievementService.getAchievementsList(searchDTO,true);

        return ResultUtils.success(new PageInfo<>(studentAchievementDTOList));
    }

    @RequestMapping("/add")
    public Result add(@RequestBody StudentAchievementDTO studentAchievementDTO) {

        studentAchievementService.addAchievement(studentAchievementDTO);

        return ResultUtils.success();
    }

    @RequestMapping("/update")
    public Result update(@RequestBody StudentAchievementDTO studentAchievementDTO) {

        studentAchievementService.update(studentAchievementDTO);

        return ResultUtils.success();


    }

    @RequestMapping("/delete")
    public Result delete(Integer id) {

        if(id == null){
            return ResultUtils.fail(EnumResponse.INVALID_PARAM);
        }

        StudentAchievementDTO studentAchievementDTO = new StudentAchievementDTO();
        studentAchievementDTO.setId(id);
        studentAchievementDTO.setDeleteFlag(EnumDeleteFlag.YES.getCode());

        studentAchievementService.update(studentAchievementDTO);

        return ResultUtils.success();

    }


}
