package com.ycj.student.service.session.impl;

import com.alibaba.fastjson.JSON;
import com.ycj.student.constants.EnumResponse;
import com.ycj.student.constants.EnumStatus;
import com.ycj.student.context.SessionContext;
import com.ycj.student.dto.account.AccountDTO;
import com.ycj.student.entity.EduAccountLogin;
import com.ycj.student.exception.LoginException;
import com.ycj.student.mapper.AccountLoginMapper;
import com.ycj.student.service.account.AccountService;
import com.ycj.student.service.session.SessionService;
import com.ycj.student.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author yangchengju
 */
@Slf4j
@Service
@Transactional
public class SessionServiceImpl implements SessionService {

    @Resource
    private AccountService accountService;
    @Resource
    private AccountLoginMapper accountLoginMapper;

    @Override
    public String login(String username, String password) throws LoginException {

        AccountDTO accountDTO = accountService.queryByUsername(username);

        if (accountDTO == null) {
            throw new LoginException(EnumResponse.NOT_EXIST_USER);
        }

        if (EnumStatus.INACTIVE.getCode().equals(accountDTO.getStatus())) {
            throw new LoginException(EnumResponse.LOGIN_INVALID_STATUS);
        }

        if (DigestUtils.md5DigestAsHex(password.getBytes()).equals(accountDTO.getPassword())) {
            log.info("{}账号校验成功！", accountDTO.getUsername());

            //使用当前系统时间作为token
            ///String token = System.currentTimeMillis() + "";
            //使用uuid作为token
            String token = UUID.randomUUID().toString().trim().replaceAll("-", "");

            EduAccountLogin eduAccountLogin = new EduAccountLogin();

            eduAccountLogin.setToken(token);
            eduAccountLogin.setUsername(accountDTO.getUsername());
            eduAccountLogin.setUserInfo(JSON.toJSONString(accountDTO));
            eduAccountLogin.setCreateTime(DateUtils.getCurrentDate());

            accountLoginMapper.insert(eduAccountLogin);
            log.info("账户登录记录插入成功");

            // 最后一次登录时间
            accountDTO.setLastLoginTime(DateUtils.getCurrentDate());
            accountService.update(accountDTO);
            log.info("账户最近一次登陆记录更新成功");

            SessionContext.setCurrentUser(accountDTO);

            return token;
        } else {
            throw new LoginException(EnumResponse.FAIL);
        }

    }
}
