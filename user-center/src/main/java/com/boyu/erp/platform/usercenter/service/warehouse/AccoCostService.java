package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.AccoCost;
import com.boyu.erp.platform.usercenter.model.wareh.CountCostModel;

import java.util.List;
import java.util.Set;

/**
 * 核算组织成本接口
 * @author HHe
 * @date 2019/10/25 10:22
 */
public interface AccoCostService {
    /**
     * 查询核算组织成本集合
     * @param prodClsIds 商品品种
     * @param fsclUnitId 会计组织Id
     * @return 核算组织成本集合
     * @author HHe
     * @date 2019/10/25 10:52
     */
    List<AccoCost> queryAccoCostListByClsAndUnit(Set<Long> prodClsIds, Long fsclUnitId);
    /**
     * 修改组织成本
     * @param countCostModel 成本数据
     * @param fsclUnitId 会计组织Id
     * @return 数据库操作数
     * @author HHe
     * @date 2019/10/25 15:16
     */
    int updateByCountCost(CountCostModel countCostModel, Long fsclUnitId);
}
