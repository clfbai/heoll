package com.boyu.erp.platform.usercenter.mapper.employye;

import com.boyu.erp.platform.usercenter.entity.employee.EmplChg;
import com.boyu.erp.platform.usercenter.entity.employee.EmpKey;

import java.util.List;

public interface EmplChgMapper {
    int deleteByPrimaryKey(EmpKey key);

    int insert(EmplChg record);

    int insertSelective(EmplChg record);

    EmplChg selectByPrimaryKey(EmpKey key);

    int updateByPrimaryKeySelective(EmplChg record);

    int updateByPrimaryKey(EmplChg record);

    List<EmplChg> selectList(EmpKey mode);
}