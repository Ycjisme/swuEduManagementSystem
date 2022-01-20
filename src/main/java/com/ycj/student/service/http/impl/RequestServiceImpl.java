package com.ycj.student.service.http.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.ycj.student.filter.RequestWrapper;
import com.ycj.student.service.http.RequestService;
import com.ycj.student.utils.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

@Service
public class RequestServiceImpl implements RequestService {

    @Override
    public String getRequestBody(HttpServletRequest request) {

        String body = null;

        if (HttpUtils.isRequestBody(request)) {
            RequestWrapper requestWrapper = new RequestWrapper(request);
            body = requestWrapper.getBody();
        }

        if (StringUtils.isEmpty(body)) {
            // 针对非requestBody接收参数的请求。
            Map<String, Object> parameterMap = Maps.newHashMap();
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String name = parameterNames.nextElement();
                parameterMap.put(name, request.getParameter(name));
            }
            body = JSON.toJSONString(parameterMap);
        }

        return body;
    }
}
