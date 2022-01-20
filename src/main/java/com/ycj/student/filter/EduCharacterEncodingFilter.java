package com.ycj.student.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yangchengju
 */
@Component
@WebFilter(filterName = "encodingFilter", urlPatterns = "/*")
@Order(100)
public class EduCharacterEncodingFilter extends OncePerRequestFilter {

    protected static final String UTF_8_CHARSET = "UTF-8";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        request.setCharacterEncoding(UTF_8_CHARSET);
        response.setCharacterEncoding(UTF_8_CHARSET);
        filterChain.doFilter(request,response);
    }
}
