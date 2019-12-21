package com.boyu.erp.platform.usercenter.mapper.dept;

import com.boyu.erp.platform.usercenter.entity.dept.DeptAttrDef;

import java.util.List;

public interface DeptAttrDefMapper {
    int deleteByPrimaryKey(String attrType);

    int insertSelective(DeptAttrDef record);

    DeptAttrDef selectByPrimaryKey(String attrType);

    List<DeptAttrDef> selectList();

    int updateByPrimaryKeySelective(DeptAttrDef record);


}