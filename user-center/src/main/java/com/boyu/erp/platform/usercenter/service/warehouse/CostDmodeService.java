package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.CostDmode;

/**
 * 影响成本出库方式业务接口
 * @author HHe
 * @date 2019/8/29 16:05
 */
public interface CostDmodeService {
    CostDmode getCostDmodeByUnitMode(Long unitId, String delivMode);
}
