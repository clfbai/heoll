package com.boyu.erp.platform.usercenter.mapper.system;

import com.boyu.erp.platform.usercenter.entity.system.CostGrp;

public interface CostGrpMapper {
    int deleteByCostGrp(CostGrp key);

    int insertCostGrp(CostGrp record);

    CostGrp selectByCostGrp(CostGrp key);

    int updateByCostGrp(CostGrp record);
    /**
     * 根据组织查询成本组
     * @author HHe
     * @date 2019/9/27 17:06
     */
    CostGrp queryCostGrpByUnitId(long unitId);
}