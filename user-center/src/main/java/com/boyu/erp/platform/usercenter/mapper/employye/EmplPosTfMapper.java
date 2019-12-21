package com.boyu.erp.platform.usercenter.mapper.employye;

import com.boyu.erp.platform.usercenter.entity.employee.EmpKey;
import com.boyu.erp.platform.usercenter.entity.employee.EmplPosTf;

import java.util.List;

public interface EmplPosTfMapper {
    int deleteByPrimaryKey(EmpKey key);

    int insert(EmplPosTf record);

    int insertSelective(EmplPosTf record);

    EmplPosTf selectByPrimaryKey(EmpKey key);

    int updateByPrimaryKeySelective(EmplPosTf record);

    int updateByPrimaryKey(EmplPosTf record);

    List<EmplPosTf> selectList(EmpKey mode);
}