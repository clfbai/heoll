package com.boyu.erp.platform.usercenter.mapper.brand;

import com.boyu.erp.platform.usercenter.entity.brand.UnitBg;

import java.util.List;

public interface UnitBgMapper {

    public List<UnitBg> unitBgList(UnitBg unitId);

    public int insertSelective(UnitBg record);

    public int updateUnitBg(UnitBg record);

    public int updateUnitBgList(UnitBg record);

    public int deleteUnitBg(UnitBg record);
}