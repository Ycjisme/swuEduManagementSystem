package com.ycj.student.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Slf4j
public class AccessTokenUtils {

    public static String getAccessToken(HttpServletRequest request, String cookieName) {
        String accessToken = request.getHeader(cookieName);
        if (StringUtils.isEmpty(accessToken)) {
            Cookie[] cookies = request.getCookies();
            if (null == cookies) {
                return "";
            }
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                    break;
                }
            }
        }
        return accessToken.replaceAll("\"", "");
    }

}
