package com.ycj.student.service.account;

import com.ycj.student.dto.account.*;
import com.ycj.student.exception.ModifyPasswordException;
import com.ycj.student.exception.UsernameExistException;

import java.util.List;

public interface AccountService {

    AccountDTO queryByUsername(String username);

    /**
     * 获取账户列表
     * @param searchDTO
     * @param isPage
     * @return
     */
    List<AccountDTO> listAccount(AccountSearchDTO searchDTO, boolean isPage);

    /**
     * 添加账户
     * @param accountDTO
     * @throws UsernameExistException
     */
    void add(AccountDTO accountDTO) throws UsernameExistException;

    void update(AccountDTO accountDTO);

    void settingAuth(AccountAuthSubmitDTO accountAuthSubmitDTO);

    void resetPassword(AccountResetPasswordDTO accountResetPasswordDTO);

    void modifyPassword(AccountModifyPasswordDTO accountModifyPasswordDTO) throws ModifyPasswordException;


}
