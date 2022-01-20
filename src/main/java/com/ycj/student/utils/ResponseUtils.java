package com.ycj.student.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
public class ResponseUtils {

    public static void write(HttpServletResponse response, String str) {
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=utf-8");
            out = response.getWriter();
            out.write(str);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }

    public static void println(HttpServletResponse response, String str) {
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=utf-8");
            out = response.getWriter();
            out.println(str);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }
}
