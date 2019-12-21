package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.CostDmode;
import org.springframework.stereotype.Repository;

/**
 * CostDmodeMapper继承基类
 */
@Repository
public interface CostDmodeMapper {
    CostDmode getCostDmodeByUnitMode(Long unitId, String delivMode);
}