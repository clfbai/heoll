package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.CostRmode;

import java.util.List;

/**
 * 类描述:
 *
 * @Description: 入库影响库存方式
 * @auther: CLF
 * @date: 2019/11/14 16:10
 */
public interface CostRmodeService {

    int addCostRmode(CostRmode costRmode);

    int deleteCostRmode(CostRmode costRmode);

    List<CostRmode> selectUnitCostRmode(Long unitId);

    CostRmode selectCostRmode(CostRmode costRmode);

    /**
     * 功能描述: 查询入库凡是取反
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/14 17:14
     */
    List<String> costRomdeFalse();
}
