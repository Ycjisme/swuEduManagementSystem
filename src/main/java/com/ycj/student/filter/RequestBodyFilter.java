package com.ycj.student.filter;

import com.ycj.student.utils.HttpUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author yangchengju
 */
@Component
@WebFilter(filterName = "requestBodyFilter", urlPatterns = "/*")
public class RequestBodyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        ServletRequest requestWrapper = null;

        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            if (HttpUtils.isRequestBody(request)) {
                requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);
            }
        }
        if (requestWrapper == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(requestWrapper, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }


}
