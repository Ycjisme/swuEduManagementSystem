package com.ycj.student.controller.account;

import com.ycj.student.context.SessionContext;
import com.ycj.student.dto.account.AccountDTO;
import com.ycj.student.service.account.menu.AccountMenuService;
import com.ycj.student.utils.Result;
import com.ycj.student.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yangchengju
 */
@RestController
@RequestMapping("/account")
public class AccountMenuController {

    @Resource
    private AccountMenuService accountMenuService;

    /**
     * 返回Json格式的menu列表，不同账户权限的menu不同
     * 管理员和校领导menu包含：首页、学生、成绩、班级、课程、账号
     * 而班主任只有：首页、学生、成绩、课程
     * @return
     */
    @RequestMapping("/menu")
    public Result queryMenu() {
        AccountDTO currentUser = SessionContext.getCurrentUser();
        return ResultUtils.success(accountMenuService.queryAccountMenu(currentUser.getAccountType()));
    }

}
