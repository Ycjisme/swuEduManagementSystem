package com.ycj.student.service.account.auth;

import java.util.List;

/**
 * @author yangchengju
 */
public interface AccountAuthService {
    /**
     * 通过account_id得到班级clazz_id列表
     * @param accountId
     * @return
     */
    List<Integer> queryClazzIdByAccountId(Integer accountId);

}
