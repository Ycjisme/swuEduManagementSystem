package com.ycj.student.service.account.menu.impl;

import com.ycj.student.entity.EduAccountMenu;
import com.ycj.student.mapper.AccountMenuMapper;
import com.ycj.student.service.account.menu.AccountMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yangchengju
 */
@Service
public class AccountMenuServiceImpl implements AccountMenuService {

    @Resource
    private AccountMenuMapper accountMenuMapper;

    @Override
    public String queryAccountMenu(Integer accountType) {

        if (accountType == null) {
            return null;
        }

        EduAccountMenu eduAccountMenu = accountMenuMapper.selectById(accountType);
        return eduAccountMenu.getMenu();
    }
}
