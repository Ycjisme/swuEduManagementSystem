package com.ycj.student.controller.clazz;

import com.github.pagehelper.PageInfo;
import com.ycj.student.constants.EnumResponse;
import com.ycj.student.context.SessionContext;
import com.ycj.student.dto.clazz.ClazzDTO;
import com.ycj.student.dto.clazz.ClazzSearchDTO;
import com.ycj.student.service.account.auth.AccountAuthService;
import com.ycj.student.service.clazz.ClazzService;
import com.ycj.student.utils.Result;
import com.ycj.student.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yangchengju
 */
@RestController
@RequestMapping("/clazz")
public class ClazzController {

    @Resource
    private ClazzService clazzService;
    @Resource
    private AccountAuthService accountAuthService;

    @RequestMapping("/list")
    public Result getClazzList(@RequestBody ClazzSearchDTO searchDTO) {

        searchDTO.setClazzIdList(accountAuthService.queryClazzIdByAccountId(SessionContext.getAccountId()));

        List<ClazzDTO> clazzDTOList = clazzService.getClazzList(searchDTO, true);

        return ResultUtils.success(new PageInfo<>(clazzDTOList));
    }

    @RequestMapping("/add")
    public Result add(@RequestBody ClazzDTO clazzDTO) {


        clazzService.addClazz(clazzDTO);

        return ResultUtils.success();
    }

    @RequestMapping("/update")
    public Result update(@RequestBody ClazzDTO clazzDTO) {

        if (clazzDTO.getId() == null) {
            return ResultUtils.fail(EnumResponse.INVALID_PARAM);
        }


        clazzService.updateClazz(clazzDTO);


        return ResultUtils.success();
    }

    @RequestMapping("/delete")
    public Result delete(Integer id) {

        if (id == null) {
            return ResultUtils.fail(EnumResponse.INVALID_PARAM);
        }

        clazzService.deleteClazz(id);

        return ResultUtils.success();
    }

}
