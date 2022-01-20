package com.ycj.student.service.student.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.ycj.student.constants.EnumDeleteFlag;
import com.ycj.student.constants.EnumResponse;
import com.ycj.student.dto.student.StudentDTO;
import com.ycj.student.dto.student.StudentDeleteDTO;
import com.ycj.student.dto.student.StudentInfoExcelSheetDTO;
import com.ycj.student.dto.student.StudentSearchDTO;
import com.ycj.student.entity.EduStudent;
import com.ycj.student.exception.IdCartNoExistException;
import com.ycj.student.mapper.StudentMapper;
import com.ycj.student.service.student.StudentService;
import com.ycj.student.utils.DateUtils;
import com.ycj.student.vo.StudentFileVo;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author yangchengju
 */
@Service
@Transactional
@Slf4j
public class StudentServiceImpl implements StudentService {

    private static final String STUDENT_INFO = "学生全量信息导出表";
    private static final int COLUMN_NUM = 10;

    @Resource
    private StudentMapper studentMapper;
    @Resource
    private MapperFacade mapperFacade;

    @Override
    public List<StudentDTO> listStudents(StudentSearchDTO searchDTO, boolean isPage) {

        if (isPage) {
            PageHelper.startPage(searchDTO.getPageNum(), searchDTO.getPageSize());
        }

        return studentMapper.listStudents(searchDTO);
    }

    @Override
    public void addStudent(StudentDTO studentDTO) throws IdCartNoExistException {


        int countNum = studentMapper.countStudentByIdCartNo(studentDTO.getIdentityCardNo(), null);
        if (countNum > 0) {
            throw new IdCartNoExistException(EnumResponse.ID_CART_NO_EXIST_EXCEPTION);
        }

        studentDTO.setDeleteFlag(EnumDeleteFlag.NO.getCode());
        studentDTO.setCreateTime(DateUtils.getCurrentDate());
        studentDTO.setUpdateTime(DateUtils.getCurrentDate());

        EduStudent eduStudent = mapperFacade.map(studentDTO, EduStudent.class);

        studentMapper.insert(eduStudent);
        log.info("Student插入成功");

    }

    @Override
    public void updateStudent(StudentDTO studentDTO) throws IdCartNoExistException {

        if (studentDTO.getId() == null) {
            return;
        }

        int count = studentMapper.countStudentByIdCartNo(studentDTO.getIdentityCardNo(), studentDTO.getId());
        if (count > 0) {
            throw new IdCartNoExistException(EnumResponse.ID_CART_NO_EXIST_EXCEPTION);
        }

        EduStudent eduStudent = mapperFacade.map(studentDTO, EduStudent.class);

        studentMapper.updateById(eduStudent);
        log.info("Student更新成功");

    }

    @Override
    public void delete(StudentDeleteDTO studentDeleteDTO) {

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(studentDeleteDTO.getId());
        studentDTO.setRemark(studentDeleteDTO.getRemark());
        studentDTO.setDeleteTime(DateUtils.getCurrentDate());
        studentDTO.setDeleteUserId(studentDeleteDTO.getDeleteUserId());
        studentDTO.setDeleteFlag(EnumDeleteFlag.YES.getCode());

        EduStudent eduStudent = mapperFacade.map(studentDTO, EduStudent.class);

        studentMapper.updateById(eduStudent);
        log.info("Student删除成功");

    }

    @Override
    public StudentFileVo downloadStudentInfo(StudentSearchDTO searchDTO) {
        StudentFileVo file = new StudentFileVo();
        List<StudentInfoExcelSheetDTO> studentInformationExcelSheet = getStudentInformationExcelSheet(searchDTO);

        Workbook workbook= getWorkBook(studentInformationExcelSheet);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
            byte[] bookBytes = outputStream.toByteArray();
            file.setFileName(STUDENT_INFO + ".xls");
            file.setFileBytes(bookBytes);
            return file;

        } catch(Exception e) {
            log.error("Error writingExcel workbook write error: {}", e);
        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
            } catch (IOException e) {
                log.error("excel work book close error {}", e);
            }

            try {
                if (null != outputStream) {
                    outputStream.close();
                }
            } catch (IOException e) {
                log.error("outputStream close error {}", e);
            }
        }
        return null;
    }

    private Workbook getWorkBook(List<StudentInfoExcelSheetDTO> studentInformationExcelSheet) {

        //HSSFWorkbook：后缀为xls，Excel 2003（excel的文档对象）
        HSSFWorkbook workbook = new HSSFWorkbook();

        //建立新的sheet对象（Excel的表单）
        HSSFSheet sheet = workbook.createSheet();

        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow headRow = sheet.createRow(0);

        //创建单元格并设置单元格内容
        headRow.createCell(0).setCellValue("学生姓名");
        headRow.createCell(1).setCellValue("年龄");
        headRow.createCell(2).setCellValue("身份证号");
        headRow.createCell(3).setCellValue("性别");
        headRow.createCell(4).setCellValue("监护人");
        headRow.createCell(5).setCellValue("手机号码");
        headRow.createCell(6).setCellValue("学号");
        headRow.createCell(7).setCellValue("班级");
        headRow.createCell(8).setCellValue("所属年级");
        headRow.createCell(9).setCellValue("居住地");
        headRow.createCell(10).setCellValue("备注");

        for(StudentInfoExcelSheetDTO studentInfo : studentInformationExcelSheet){
            HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
            row.createCell(0).setCellValue(studentInfo.getName());
            row.createCell(1).setCellValue(studentInfo.getAge());
            row.createCell(2).setCellValue(studentInfo.getIdentityCardNo());
            row.createCell(3).setCellValue(studentInfo.getSexStr());
            row.createCell(4).setCellValue(studentInfo.getGuardian());
            row.createCell(5).setCellValue(studentInfo.getMobile());
            row.createCell(6).setCellValue(studentInfo.getStudentNo());
            row.createCell(7).setCellValue(studentInfo.getClazzName());
            row.createCell(8).setCellValue(studentInfo.getDepartment());
            row.createCell(9).setCellValue(studentInfo.getAllAddress());
            row.createCell(10).setCellValue(studentInfo.getRemark());
        }
        //设置自适应列宽
        for (int i = 0; i < COLUMN_NUM; i++) {
            sheet.autoSizeColumn((short)i);
        }

        return workbook;
    }

    private List<StudentInfoExcelSheetDTO> getStudentInformationExcelSheet(StudentSearchDTO searchDTO) {

        List<StudentDTO> studentDTOList = listStudents(searchDTO, false);

        if (CollectionUtils.isEmpty(studentDTOList)) {
            return Lists.newArrayList();
        }
        List<StudentInfoExcelSheetDTO> excelSheetDTOS = mapperFacade.mapAsList(studentDTOList, StudentInfoExcelSheetDTO.class);
        excelSheetDTOS.stream().forEach(item ->{
            item.setSexStr(new Integer(1).equals(item.getSex()) ? "男" : "女");
            item.setAllAddress(AllAddress(item));
        });
        return excelSheetDTOS;
    }

    private String AllAddress(StudentInfoExcelSheetDTO item) {
        return StrUtil.emptyToDefault(item.getProvince(), "") + StrUtil.emptyToDefault(item.getCity(), "") + item.getAddress();
    }

}
