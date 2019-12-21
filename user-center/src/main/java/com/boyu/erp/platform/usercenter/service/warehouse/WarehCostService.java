package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehCost;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehStk;
import com.boyu.erp.platform.usercenter.model.wareh.CountCostModel;

import java.util.List;
import java.util.Set;

/**
 * 仓库成本接口
 *
 * @author HHe
 * @date 2019/10/25 10:25
 */
public interface WarehCostService {
    /**
     * 根据商品品种集合和仓库查询仓库成本集合
     *
     * @author HHe
     * @date 2019/10/25 10:28
     */
    List<WarehCost> queryWarehCostListByClsAndWareh(Set<Long> prodClsIds, Long warehId);

    /**
     * 修改仓库成本
     *
     * @param countCostModel 修改的数据
     * @param warehId        对应仓库
     * @return 数据库操作数
     * @author HHe
     * @date 2019/10/25 14:56
     */
    int updateByCountCost(CountCostModel countCostModel, Long warehId);

    /**
     * 添加修改库存成本
     *
     * @param countCosts 包含仓库仓库成本、仓库仓库数量、仓库商品金额
     * @param warehId    仓库Id
     * @param fsclUnitId 会计组织Id
     * @return 数据库执行数
     * @author HHe
     * @date 2019/11/14 10:05
     */
    int insertUpdateWarehCost(List<CountCostModel> countCosts,Long warehId,Long fsclUnitId);

    /**
     * 功能描述: 增加或修改库存成本
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/18 15:42
     */
    int addUpadteWarehCostList(List<WarehCost> warehCosts);
    /**
     * 修改仓库成本数量
     * @param warehStkList 商品Id、减少数量（已负）
     * @param warehId 仓库Id
     * @return 数据库操作数
     * @author HHe
     * @date 2019/11/22 11:36
     */
    int updateByWarehStk(List<WarehStk> warehStkList, Long warehId);
    /**
     * 减少数量
     * @author HHe
     * @date 2019/11/22 12:26
     */
    int updateByQty(List<CountCostModel> countCostModels, Long warehId);
}
