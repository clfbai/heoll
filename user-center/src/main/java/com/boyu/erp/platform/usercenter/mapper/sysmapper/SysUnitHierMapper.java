package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysUnitHier;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;

import java.util.List;

public interface SysUnitHierMapper {
    int deleteByPrimaryKey(String unitHierId);

    int insert(SysUnitHier record);

    int insertSelective(SysUnitHier record);

    SysUnitHier selectByPrimaryKey(String unitHierId);

    int updateByPrimaryKeySelective(SysUnitHier record);

    int updateByPrimaryKey(SysUnitHier record);

    List<SysUnitHier> getSysUnitHierList(SysUnitHier record);

    List<PurKeyValue> optionGet();
}