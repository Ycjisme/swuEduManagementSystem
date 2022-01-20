package com.ycj.student.service.account.login.impl;

import com.alibaba.fastjson.JSON;
import com.ycj.student.dto.account.AccountDTO;
import com.ycj.student.dto.account.login.AccountLoginDTO;
import com.ycj.student.mapper.AccountLoginMapper;
import com.ycj.student.service.account.login.AccountLoginService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
@Transactional
public class AccountLoginServiceImpl implements AccountLoginService {

    @Resource
    private AccountLoginMapper accountLoginMapper;

    @Override
    public AccountDTO queryAccountByToken(String token) {

        if (StringUtils.isEmpty(token)) {
            return null;
        }
        AccountLoginDTO accountLoginDTO = accountLoginMapper.queryAccountByToken(token);

        if (accountLoginDTO == null) {
            return null;
        }

        return JSON.parseObject(accountLoginDTO.getUserInfo(), AccountDTO.class);
    }
}
