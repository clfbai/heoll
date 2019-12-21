package com.boyu.erp.platform.usercenter.mapper.employye;

import com.boyu.erp.platform.usercenter.entity.employee.EmpKey;
import com.boyu.erp.platform.usercenter.entity.employee.EmplWorkExp;

import java.util.List;

public interface EmplWorkExpMapper {
    int deleteByPrimaryKey(EmpKey key);

    int insertSelective(EmplWorkExp record);

    EmplWorkExp selectByPrimaryKey(EmpKey key);

   List<EmplWorkExp> selectEmplWorkExp(EmpKey key);

    int updateByPrimaryKeySelective(EmplWorkExp record);


}