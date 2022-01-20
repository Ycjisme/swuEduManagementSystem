package com.ycj.student.controller.parameter;

import com.github.pagehelper.PageInfo;
import com.ycj.student.constants.EnumResponse;
import com.ycj.student.dto.parameter.ParameterDTO;
import com.ycj.student.service.parameter.ParameterService;
import com.ycj.student.utils.Result;
import com.ycj.student.utils.ResultUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yangchengju
 */
@RestController
@RequestMapping("/parameter")
public class ParameterController {

    @Resource
    private ParameterService parameterService;

    @RequestMapping("/list")
    public Result list(String parameterType) {

        if(StringUtils.isEmpty(parameterType)){
            return ResultUtils.fail(EnumResponse.INVALID_PARAM);
        }

        List<ParameterDTO> parameterDTOList = parameterService.list(parameterType);

        return ResultUtils.success(new PageInfo<>(parameterDTOList));

    }

}
