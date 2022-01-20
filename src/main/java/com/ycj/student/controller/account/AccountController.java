package com.ycj.student.controller.account;

import com.github.pagehelper.PageInfo;
import com.ycj.student.constants.EnumResponse;
import com.ycj.student.context.SessionContext;
import com.ycj.student.dto.account.*;
import com.ycj.student.exception.ModifyPasswordException;
import com.ycj.student.exception.UsernameExistException;
import com.ycj.student.service.account.AccountService;
import com.ycj.student.utils.Result;
import com.ycj.student.utils.ResultUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yangchengju
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    private AccountService accountService;


    /**
     * 查询账号信息，在账户页面使用
     */
    @RequestMapping("/list")
    public Result list(@RequestBody AccountSearchDTO searchDTO) {

        List<AccountDTO> accountDTOList = accountService.listAccount(searchDTO, true);

        accountDTOList.stream().forEach(item->item.setPassword(null));

        PageInfo pageInfo = new PageInfo(accountDTOList);

        return ResultUtils.success(pageInfo);
    }

    /**
     * 查询账号信息，登录时调用
     */
    @RequestMapping("/query")
    public Result query() {
        AccountDTO accountDTO = SessionContext.getCurrentUser();
        //设置为空 目的是：不显示密码
        accountDTO.setPassword(null);
        return ResultUtils.success(accountDTO);
    }

    /**
     * 添加账户
     * @param accountDTO
     * @return
     */
    @RequestMapping("/add")
    public Result addAccount(@RequestBody AccountDTO accountDTO) {

        //健壮性判断
        if (StringUtils.isEmpty(accountDTO.getUsername()) || StringUtils.isEmpty(accountDTO.getPassword())) {
            return ResultUtils.fail(EnumResponse.INVALID_PARAM);
        }

        //手动处理用户名重复异常
        try {
            accountService.add(accountDTO);
        } catch (UsernameExistException e) {
            return ResultUtils.fail(e.getEnumResponse());
        }

        return ResultUtils.success();
    }

    /**
     * 更新账户
     * @param accountDTO
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody AccountDTO accountDTO) {

        accountService.update(accountDTO);

        return ResultUtils.success();
    }

    /**
     * 设置权限（可查看的班级）
     * @param accountAuthSubmitDTO
     * @return
     */
    @RequestMapping("/setting/auth")
    public Result settingAuth(@RequestBody AccountAuthSubmitDTO accountAuthSubmitDTO) {

        if (accountAuthSubmitDTO.getId() == null) {
            return ResultUtils.fail(EnumResponse.INVALID_PARAM);
        }

        accountService.settingAuth(accountAuthSubmitDTO);

        return ResultUtils.success();
    }

    /**
     * 重置密码，无需输入原始密码
     * @param accountResetPasswordDTO
     * @return
     */
    @RequestMapping("/reset/password")
    public Result resetPassword(@RequestBody AccountResetPasswordDTO accountResetPasswordDTO) {

        if (accountResetPasswordDTO.getId() == null || StringUtils.isEmpty(accountResetPasswordDTO.getPassword())) {
            return ResultUtils.fail(EnumResponse.INVALID_PARAM);
        }

        accountService.resetPassword(accountResetPasswordDTO);

        return ResultUtils.success();
    }

    /**
     * 修改密码，需要输入原始密码
     * @param accountModifyPasswordDTO
     * @return
     */
    @RequestMapping("/modify/password")
    public Result modifyPassword(@RequestBody AccountModifyPasswordDTO accountModifyPasswordDTO) {

        //健壮性判断
        if (StringUtils.isEmpty(accountModifyPasswordDTO.getOldPassword()) || StringUtils.isEmpty(accountModifyPasswordDTO.getNewPassword())) {
            return ResultUtils.fail(EnumResponse.INVALID_PARAM);
        }
        // 设置当前账号ID
        accountModifyPasswordDTO.setId(SessionContext.getAccountId());

        //手动处理输入的原始密码不正确异常
        try {
            accountService.modifyPassword(accountModifyPasswordDTO);
        } catch (ModifyPasswordException e) {
            return ResultUtils.fail(e.getEnumResponse());
        }

        return ResultUtils.success();
    }

}
