package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysUnitHierDtl;

public interface SysUnitHierDtlMapper {
    int deleteByPrimaryKey(SysUnitHierDtl key);

    int insert(SysUnitHierDtl record);

    int insertSelective(SysUnitHierDtl record);

    SysUnitHierDtl selectByPrimaryKey(SysUnitHierDtl key);

    int updateByPrimaryKeySelective(SysUnitHierDtl record);

    int updateByPrimaryKey(SysUnitHierDtl record);
}