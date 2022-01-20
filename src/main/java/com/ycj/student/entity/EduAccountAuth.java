package com.ycj.student.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "edu_account_auth")
public class EduAccountAuth {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer accountId;
    private Integer clazzId;

}
