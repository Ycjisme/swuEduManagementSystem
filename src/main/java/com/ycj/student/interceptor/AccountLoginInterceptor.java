package com.ycj.student.interceptor;

import com.alibaba.excel.util.DateUtils;
import com.alibaba.fastjson.JSON;
import com.ycj.student.constants.EnumResponse;
import com.ycj.student.context.SessionContext;
import com.ycj.student.dto.account.AccountDTO;
import com.ycj.student.service.account.login.AccountLoginService;
import com.ycj.student.service.http.RequestService;
import com.ycj.student.utils.AccessTokenUtils;
import com.ycj.student.utils.IpUtils;
import com.ycj.student.utils.ResponseUtils;
import com.ycj.student.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;

@Component
@Slf4j
public class AccountLoginInterceptor extends HandlerInterceptorAdapter {


    @Resource
    private AccountLoginService accountLoginService;
    @Resource
    private RequestService requestService;

    private String[] excludePath = {"/api/user/login"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String requestURL = request.getRequestURI();

        String body = requestService.getRequestBody(request);

        for (String url : excludePath) {
            if (requestURL.startsWith(url) || requestURL.contains(url)) {
                // 登录相关的不拦截
                return true;
            }
        }

        String method = request.getMethod();
        if ("OPTIONS".equalsIgnoreCase(method)) {
            return true;
        }

        try {
            if (new Date().compareTo(DateUtils.parseDate("2022-10-01 23:59:59")) >= 1) {
                ResponseUtils.write(response, JSON.toJSONString(ResultUtils.fail(EnumResponse.SOFTWARE_TIMEOUT_EXCEPTION)));
                return false;
            }
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }

        String token = AccessTokenUtils.getAccessToken(request, "token");
        if (StringUtils.isEmpty(token)) {
            ResponseUtils.write(response, JSON.toJSONString(ResultUtils.fail(EnumResponse.USER_TOKEN_TIMEOUT)));
            return false;
        }

        AccountDTO accountDTO = accountLoginService.queryAccountByToken(token);

        if (accountDTO == null) {
            ResponseUtils.write(response, JSON.toJSONString(ResultUtils.fail(EnumResponse.USER_TOKEN_TIMEOUT)));
            return false;
        } else {
            log.info("----------------当前请求IP：{}，当前登录账号：{}，请求url：{}，请求参数：{} ",IpUtils.getIpAddr(request), accountDTO.getUsername(), requestURL, body);
            SessionContext.setCurrentUser(accountDTO);
            return true;
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //上下文属性值清除
        SessionContext.removeCurrentUser();
        super.afterCompletion(request, response, handler, ex);
    }

}
