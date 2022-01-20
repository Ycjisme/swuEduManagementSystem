package com.ycj.student.context;

import com.ycj.student.dto.account.AccountDTO;

/**
 * @author yangchengju
 */
public class SessionContext {

    private static final ThreadLocal<AccountDTO> userThreadLocal = new ThreadLocal<>();


    public static void setCurrentUser(AccountDTO accountDTO) {
        if (accountDTO != null) {
            userThreadLocal.set(accountDTO);
        }
    }


    public static AccountDTO getCurrentUser() {
        return userThreadLocal.get();
    }


    public static Integer getAccountId() {
        AccountDTO accountDTO = userThreadLocal.get();
        if (null != accountDTO) {
            return accountDTO.getId();
        }
        return null;
    }

    public static String getUsername() {
        AccountDTO accountDTO = userThreadLocal.get();
        if (null != accountDTO) {
            return accountDTO.getUsername();
        }
        return null;
    }


    public static void removeCurrentUser() {
        userThreadLocal.remove();
    }


}
