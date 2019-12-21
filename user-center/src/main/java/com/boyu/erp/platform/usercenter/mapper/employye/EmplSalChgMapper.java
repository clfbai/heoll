package com.boyu.erp.platform.usercenter.mapper.employye;

import com.boyu.erp.platform.usercenter.entity.employee.EmpKey;
import com.boyu.erp.platform.usercenter.entity.employee.EmplSalChg;

import java.util.List;

public interface EmplSalChgMapper {
    int deleteByPrimaryKey(EmpKey key);

    int insert(EmplSalChg record);

    int insertSelective(EmplSalChg record);

    EmplSalChg selectByPrimaryKey(EmpKey key);

    int updateByPrimaryKeySelective(EmplSalChg record);

    int updateByPrimaryKey(EmplSalChg record);

    List<EmplSalChg> selectList(EmpKey mode);
}