package com.ycj.student.service.http;

import javax.servlet.http.HttpServletRequest;

public interface RequestService {

    String getRequestBody(HttpServletRequest request);


}
