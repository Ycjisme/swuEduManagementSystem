package com.ycj.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycj.student.dto.student.StudentDTO;
import com.ycj.student.dto.student.StudentSearchDTO;
import com.ycj.student.entity.EduStudent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMapper extends BaseMapper<EduStudent> {


    List<StudentDTO> listStudents(StudentSearchDTO searchDTO);

    int countStudentByIdCartNo(@Param("identityCardNo") String identityCardNo,@Param("id") Long id);

}
