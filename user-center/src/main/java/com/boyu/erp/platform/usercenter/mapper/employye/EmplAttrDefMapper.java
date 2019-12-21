package com.boyu.erp.platform.usercenter.mapper.employye;

import com.boyu.erp.platform.usercenter.entity.employee.EmplAttrDef;

import java.util.List;

public interface EmplAttrDefMapper {
    int deleteByPrimaryKey(String attrType);

    int insert(EmplAttrDef record);

    int insertSelective(EmplAttrDef record);

    EmplAttrDef selectByPrimaryKey(String attrType);

    int updateByPrimaryKeySelective(EmplAttrDef record);

    int updateByPrimaryKey(EmplAttrDef record);

    List<EmplAttrDef> getList();
}