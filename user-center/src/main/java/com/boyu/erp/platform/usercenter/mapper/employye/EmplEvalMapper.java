package com.boyu.erp.platform.usercenter.mapper.employye;

import com.boyu.erp.platform.usercenter.entity.employee.EmpKey;
import com.boyu.erp.platform.usercenter.entity.employee.EmplEval;

import java.util.List;

public interface EmplEvalMapper {
    int deleteByPrimaryKey(EmpKey key);

    int insert(EmplEval record);

    int insertSelective(EmplEval record);

    EmplEval selectByPrimaryKey(EmpKey key);

    int updateByPrimaryKeySelective(EmplEval record);

    int updateByPrimaryKey(EmplEval record);

    List<EmplEval> selectList(EmpKey mode);
}