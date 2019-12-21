package com.boyu.erp.platform.usercenter.mapper.employye;

import com.boyu.erp.platform.usercenter.entity.employee.EmpKey;
import com.boyu.erp.platform.usercenter.entity.employee.EmplEduH;

import java.util.List;

public interface EmplEduHMapper {
    int deleteByPrimaryKey(EmpKey key);

    int insert(EmplEduH record);

    int insertSelective(EmplEduH record);

    EmplEduH selectByPrimaryKey(EmpKey key);

    int updateByPrimaryKeySelective(EmplEduH record);

    int updateByPrimaryKey(EmplEduH record);

     List<EmplEduH> selectList(EmpKey mode);
}