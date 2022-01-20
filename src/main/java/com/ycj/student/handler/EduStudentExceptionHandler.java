package com.ycj.student.handler;

import com.ycj.student.constants.EnumResponse;
import com.ycj.student.utils.Result;
import com.ycj.student.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class EduStudentExceptionHandler {

    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public Result doThrowableHandle(Throwable e) {
        log.error("【系统异常】{}", e);
        return ResultUtils.fail(EnumResponse.SYSTEM_EXCEPTION);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        log.error("【系统异常】{}", e);
        return ResultUtils.fail(EnumResponse.SYSTEM_EXCEPTION);
    }

}
