package com.ycj.student.service.session;

import com.ycj.student.exception.LoginException;

/**
 * @author yangchengju
 */
public interface SessionService {

    String login(String username,String password) throws LoginException;

}
