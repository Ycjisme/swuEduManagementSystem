package com.ycj.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycj.student.dto.account.AccountDTO;
import com.ycj.student.dto.account.AccountSearchDTO;
import com.ycj.student.entity.EduAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountMapper extends BaseMapper<EduAccount> {

    AccountDTO queryByUsername(@Param("username") String username);

    List<AccountDTO> getAccountList(AccountSearchDTO searchDTO);

}
