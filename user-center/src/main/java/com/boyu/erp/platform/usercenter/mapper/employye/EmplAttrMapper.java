package com.boyu.erp.platform.usercenter.mapper.employye;

import com.boyu.erp.platform.usercenter.entity.employee.EmplAttr;

public interface EmplAttrMapper {
    int deleteByPrimaryKey(EmplAttr key);

    int insert(EmplAttr record);

    int insertSelective(EmplAttr record);

    EmplAttr selectByPrimaryKey(EmplAttr key);

    int updateByPrimaryKeySelective(EmplAttr record);

    int updateByPrimaryKey(EmplAttr record);
}