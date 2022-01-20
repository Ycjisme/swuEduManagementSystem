package com.ycj.student.service.student;

import com.ycj.student.dto.student.StudentDTO;
import com.ycj.student.dto.student.StudentDeleteDTO;
import com.ycj.student.dto.student.StudentSearchDTO;
import com.ycj.student.exception.IdCartNoExistException;
import com.ycj.student.vo.StudentFileVo;

import java.util.List;

/**
 * @author yangchengju
 */
public interface StudentService {

    List<StudentDTO> listStudents(StudentSearchDTO searchDTO,boolean isPage);

    void addStudent(StudentDTO studentDTO) throws IdCartNoExistException;

    void updateStudent(StudentDTO studentDTO) throws IdCartNoExistException;

    void delete(StudentDeleteDTO studentDeleteDTO);

    StudentFileVo downloadStudentInfo(StudentSearchDTO searchDTO);

}
