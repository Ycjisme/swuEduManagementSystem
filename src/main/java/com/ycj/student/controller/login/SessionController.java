package com.ycj.student.controller.login;

import com.ycj.student.constants.EnumResponse;
import com.ycj.student.dto.LoginDTO;
import com.ycj.student.dto.LogoutDTO;
import com.ycj.student.exception.LoginException;
import com.ycj.student.service.session.SessionService;
import com.ycj.student.utils.Result;
import com.ycj.student.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yangchengju
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class SessionController {

    @Resource
    private SessionService sessionService;

    @RequestMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO) {

        if(StringUtils.isEmpty(loginDTO.getUsername()) || StringUtils.isEmpty(loginDTO.getPassword())){
            return ResultUtils.fail(EnumResponse.INVALID_PARAM);
        }

        try {
            String token = sessionService.login(loginDTO.getUsername(),loginDTO.getPassword());
            return ResultUtils.success(token);
        } catch (LoginException e) {
            log.error("登录异常！",e);
            return ResultUtils.fail(e.getEnumResponse());
        }
    }

    @RequestMapping("/logout")
    public Result logout(@RequestBody LogoutDTO logoutDTO) {
        return ResultUtils.success();
    }

}
