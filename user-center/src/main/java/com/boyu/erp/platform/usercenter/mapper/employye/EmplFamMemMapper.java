package com.boyu.erp.platform.usercenter.mapper.employye;

import com.boyu.erp.platform.usercenter.entity.employee.EmpKey;
import com.boyu.erp.platform.usercenter.entity.employee.EmplFamMem;

import java.util.List;

public interface EmplFamMemMapper {
    int deleteByPrimaryKey(EmpKey key);

    int insert(EmplFamMem record);

    int insertSelective(EmplFamMem record);

    EmplFamMem selectByPrimaryKey(EmpKey key);

    List<EmplFamMem> selectEmplFamMem(EmpKey key);

    int updateByPrimaryKeySelective(EmplFamMem record);

    int updateByPrimaryKey(EmplFamMem record);
}