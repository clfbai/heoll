package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.CostGrp;
/**
 * 成本组服务
 * @author HHe
 * @date 2019/9/27 17:01
 */
public interface CostGrpService {
    /**
     * 根据组织查询成本组
     * @author HHe
     * @date 2019/9/27 17:01
     */
    CostGrp queryCostGrpByUnitId(long unitId);
}
