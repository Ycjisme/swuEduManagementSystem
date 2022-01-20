package com.ycj.student.service.account.login;

import com.ycj.student.dto.account.AccountDTO;

public interface AccountLoginService {

    AccountDTO queryAccountByToken(String token);

}
