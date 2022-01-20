package com.ycj.student.service.account.auth.impl;

import com.google.common.collect.Lists;
import com.ycj.student.dto.account.auth.AccountAuthDTO;
import com.ycj.student.mapper.AccountAuthMapper;
import com.ycj.student.service.account.auth.AccountAuthService;
import java.util.Collections;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangchengju
 */
@Service
public class AccountAuthServiceImpl implements AccountAuthService {

    @Resource
    private AccountAuthMapper accountAuthMapper;

    @Override
    public List<Integer> queryClazzIdByAccountId(Integer accountId) {
        if(null == accountId){
            return Collections.emptyList();
        }

        List<AccountAuthDTO> accountAuthDTOList = accountAuthMapper.getAuthListByAccountIds(Lists.newArrayList(accountId));

        if(CollectionUtils.isEmpty(accountAuthDTOList)){
            return Collections.emptyList();
        }

        return accountAuthDTOList.stream().map(AccountAuthDTO::getClazzId).distinct().collect(Collectors.toList());
    }
}
