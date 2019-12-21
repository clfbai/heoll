package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.CostRmode;

import java.util.List;

public interface CostRmodeMapper {
    int deleteByPrimaryKey(CostRmode key);

    int insert(CostRmode record);

    List<CostRmode> selectUnitId(Long unitId);

    CostRmode selectCostRmode(CostRmode record);


}