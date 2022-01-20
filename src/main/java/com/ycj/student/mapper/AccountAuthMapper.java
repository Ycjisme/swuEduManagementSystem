package com.ycj.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycj.student.dto.account.auth.AccountAuthDTO;
import com.ycj.student.entity.EduAccountAuth;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountAuthMapper extends BaseMapper<EduAccountAuth> {


    void deleteByAccountId(@Param("accountId") Integer accountId);

    List<AccountAuthDTO> getAuthListByAccountIds(@Param("accountIdList") List<Integer> accountIdList);

}
