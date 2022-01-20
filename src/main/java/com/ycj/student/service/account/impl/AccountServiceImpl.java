package com.ycj.student.service.account.impl;

import com.github.pagehelper.PageHelper;
import com.ycj.student.constants.EnumResponse;
import com.ycj.student.constants.EnumStatus;
import com.ycj.student.dto.account.*;
import com.ycj.student.dto.account.auth.AccountAuthDTO;
import com.ycj.student.entity.EduAccount;
import com.ycj.student.entity.EduAccountAuth;
import com.ycj.student.exception.ModifyPasswordException;
import com.ycj.student.exception.UsernameExistException;
import com.ycj.student.mapper.AccountAuthMapper;
import com.ycj.student.mapper.AccountMapper;
import com.ycj.student.service.account.AccountService;
import com.ycj.student.utils.DateUtils;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangchengju
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;
    @Resource
    private AccountAuthMapper accountAuthMapper;
    @Resource
    private MapperFacade mapperFacade;

    @Override
    public AccountDTO queryByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return new AccountDTO();
        }
        return accountMapper.queryByUsername(username);
    }

    @Override
    public void add(AccountDTO accountDTO) throws UsernameExistException {

        accountDTO.setCreateTime(DateUtils.getCurrentDate());
        accountDTO.setLastLoginTime(DateUtils.getCurrentDate());
        accountDTO.setStatus(EnumStatus.ACTIVE.getCode());
        // 密码MD5加密
        accountDTO.setPassword(getEncryptPassword(accountDTO.getPassword()));

        //判断用户名是否重复
        AccountDTO dbAccountDTO = accountMapper.queryByUsername(accountDTO.getUsername());
        if(dbAccountDTO != null){
            throw new UsernameExistException(EnumResponse.USERNAME_EXIST_EXCEPTION);
        }

        EduAccount eduAccount = mapperFacade.map(accountDTO, EduAccount.class);

        accountMapper.insert(eduAccount);


    }

    @Override
    public void update(AccountDTO accountDTO) {

        EduAccount eduAccount = mapperFacade.map(accountDTO, EduAccount.class);

        accountMapper.updateById(eduAccount);

    }

    @Override
    public void settingAuth(AccountAuthSubmitDTO accountAuthSubmitDTO) {

        accountAuthMapper.deleteByAccountId(accountAuthSubmitDTO.getId());

        List<Integer> clazzIdList = accountAuthSubmitDTO.getClazzIdList();

        //clazzIdList为空代表拥有全部权限
        if (CollectionUtils.isEmpty(clazzIdList)) {
            return;
        }

        for (Integer clazzId : clazzIdList) {
            EduAccountAuth eduAccountAuth = new EduAccountAuth();
            eduAccountAuth.setClazzId(clazzId);
            eduAccountAuth.setAccountId(accountAuthSubmitDTO.getId());
            accountAuthMapper.insert(eduAccountAuth);
        }
    }

    @Override
    public void resetPassword(AccountResetPasswordDTO accountResetPasswordDTO) {

        EduAccount eduAccount = new EduAccount();
        eduAccount.setId(accountResetPasswordDTO.getId());
        eduAccount.setPassword(getEncryptPassword(accountResetPasswordDTO.getPassword()));

        accountMapper.updateById(eduAccount);
    }

    @Override
    public void modifyPassword(AccountModifyPasswordDTO accountModifyPasswordDTO) throws ModifyPasswordException {

        EduAccount dbAccount = accountMapper.selectById(accountModifyPasswordDTO.getId());

        //判断输入的密码与数据库表中保存的密码是否一致
        if(!dbAccount.getPassword().equals(getEncryptPassword(accountModifyPasswordDTO.getOldPassword()))){
            throw new ModifyPasswordException(EnumResponse.OLD_PASSWORD_ERROR_EXCEPTION);
        }

        EduAccount eduAccount = new EduAccount();
        eduAccount.setId(accountModifyPasswordDTO.getId());
        eduAccount.setPassword(getEncryptPassword(accountModifyPasswordDTO.getNewPassword()));

        accountMapper.updateById(eduAccount);
    }

    @Override
    public List<AccountDTO> listAccount(AccountSearchDTO searchDTO, boolean isPage) {

        if (isPage) {
            PageHelper.startPage(searchDTO.getPageNum(), searchDTO.getPageSize());
        }

        List<AccountDTO> accountDTOList = accountMapper.getAccountList(searchDTO);

        if (CollectionUtils.isEmpty(accountDTOList)) {
            return accountDTOList;
        }

        //提取id
        List<Integer> accountIdList = accountDTOList.stream().map(AccountDTO::getId).collect(Collectors.toList());

        List<AccountAuthDTO> authList = accountAuthMapper. getAuthListByAccountIds(accountIdList);

        for (AccountDTO accountDTO : accountDTOList) {
            accountDTO.setAuthList(authList.stream().filter(item -> item.getAccountId().equals(accountDTO.getId())).collect(Collectors.toList()));
        }

        return accountDTOList;

    }

    /**
     * 加密
     * @param password
     * @return
     */
    private String getEncryptPassword(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

}
