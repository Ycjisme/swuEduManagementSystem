package com.ycj.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycj.student.dto.account.login.AccountLoginDTO;
import com.ycj.student.entity.EduAccountLogin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountLoginMapper extends BaseMapper<EduAccountLogin> {


    AccountLoginDTO queryAccountByToken(@Param("token") String token);


}
