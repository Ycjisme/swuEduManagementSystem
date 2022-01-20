package com.ycj.student.controller.student;

import com.github.pagehelper.PageInfo;
import com.ycj.student.constants.EnumAccountType;
import com.ycj.student.constants.EnumDeleteFlag;
import com.ycj.student.constants.EnumResponse;
import com.ycj.student.context.SessionContext;
import com.ycj.student.dto.account.AccountDTO;
import com.ycj.student.dto.student.StudentDTO;
import com.ycj.student.dto.student.StudentDeleteDTO;
import com.ycj.student.dto.student.StudentSearchDTO;
import com.ycj.student.exception.IdCartNoExistException;
import com.ycj.student.service.account.auth.AccountAuthService;
import com.ycj.student.service.student.StudentService;
import com.ycj.student.utils.Result;
import com.ycj.student.utils.ResultUtils;
import com.ycj.student.vo.StudentFileVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author yangchengju
 */
@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {


    @Resource
    private StudentService studentService;
    @Resource
    private AccountAuthService accountAuthService;

    @RequestMapping("/list")
    public Result list(@RequestBody StudentSearchDTO searchDTO) {

        Integer accountType = SessionContext.getCurrentUser().getAccountType();
        if(EnumAccountType.COMMON_USER.getCode().equals(accountType)){
            searchDTO.setDeleteFlag(EnumDeleteFlag.NO.getCode());
        }

        searchDTO.setClazzIdList(accountAuthService.queryClazzIdByAccountId(SessionContext.getAccountId()));

        List<StudentDTO> studentDTOList = studentService.listStudents(searchDTO, true);

        return ResultUtils.success(new PageInfo<>(studentDTOList));

    }

    @RequestMapping("/add")
    public Result add(@RequestBody StudentDTO studentDTO) {

        AccountDTO currentUser = SessionContext.getCurrentUser();

        studentDTO.setCreateUserId(currentUser.getId());

        try {
            studentService.addStudent(studentDTO);
        } catch (IdCartNoExistException e) {
            log.error("身份证号已经存在",e);
            return ResultUtils.fail(e.getEnumResponse());
        }

        return ResultUtils.success();

    }

    @RequestMapping("/update")
    public Result update(@RequestBody StudentDTO studentDTO) {

        if (studentDTO.getId() == null) {
            return ResultUtils.fail(EnumResponse.INVALID_PARAM);
        }

        try {
            studentService.updateStudent(studentDTO);
        } catch (IdCartNoExistException e) {
            return ResultUtils.fail(e.getEnumResponse());
        }

        return ResultUtils.success();

    }

    @RequestMapping("/delete")
    public Result delete(@RequestBody StudentDeleteDTO studentDeleteDTO) {

        if (studentDeleteDTO.getId() == null || StringUtils.isEmpty(studentDeleteDTO.getRemark())) {
            return ResultUtils.fail(EnumResponse.INVALID_PARAM);
        }

        studentDeleteDTO.setDeleteUserId(SessionContext.getAccountId());

        studentService.delete(studentDeleteDTO);

        return ResultUtils.success();

    }

    /**
     * 导出学生信息Excel表格
     * @param searchDTO
     * @param request
     * @param response
     */
    @GetMapping("/export")
    public void getStudentInformationExcelSheet(StudentSearchDTO searchDTO, HttpServletRequest request, HttpServletResponse response){

        searchDTO.setClazzIdList(accountAuthService.queryClazzIdByAccountId(SessionContext.getAccountId()));
        StudentFileVo studentFileVo = studentService.downloadStudentInfo(searchDTO);
        if(null == studentFileVo){
            return;
        }

        OutputStream os = null;
        try {
            response.setCharacterEncoding(request.getCharacterEncoding());
            response.setContentType("application/octet-stream");
            response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment;fileName="
                    + new String(studentFileVo.getFileName().getBytes("UTF-8"), "iso-8859-1"));
            os = response.getOutputStream();
            os.write(studentFileVo.getFileBytes());
            os.flush();
        } catch(IOException e) {
            log.error("导出学生信息异常: {}", e);
        }finally {
            if(null != os){
                try{
                    os.close();
                } catch (IOException e) {
                    log.error("关闭流异常: {}", e);
                }
            }
        }
    }
}
