package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehCost;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehStk;
import com.boyu.erp.platform.usercenter.mapper.warehouse.WarehCostMapper;
import com.boyu.erp.platform.usercenter.model.wareh.CountCostModel;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehCostService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * 仓库成本服务
 *
 * @author HHe
 * @date 2019/10/25 10:25
 */
@Service
@Transactional
public class WarehCostServiceImpl implements WarehCostService {
    @Autowired
    private WarehCostMapper warehCostMapper;

    /**
     * 查询仓库成本集合
     *
     * @param prodClsIds 商品品种集合
     * @param warehId    仓库Id
     * @return 仓库成本集合
     * @author HHe
     * @date 2019/10/25 10:35
     */
    @Transactional(readOnly = true)
    @Override
    public List<WarehCost> queryWarehCostListByClsAndWareh(Set<Long> prodClsIds, Long warehId) {
        return warehCostMapper.queryWarehCostListByClsAndWareh(prodClsIds, warehId);
    }

    /**
     * 修改仓库成本
     *
     * @param countCostModel 修改的数据
     * @param warehId        对应仓库
     * @return 数据库操作数
     * @author HHe
     * @date 2019/10/25 14:56
     */
    @Override
    public int updateByCountCost(CountCostModel countCostModel, Long warehId) {
        WarehCost warehCost = new WarehCost();
        warehCost.setProdClsId(countCostModel.getProdClsId());
        warehCost.setWarehId(warehId);
        warehCost.setTotQty(countCostModel.getWarehQty());
        warehCost.setTotVal(countCostModel.getWarehVal());
        warehCost.setWarehCost(countCostModel.getWarehCost());
        return warehCostMapper.updateByPrimaryKeySelective(warehCost);
    }

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
    @Override
    public int insertUpdateWarehCost(List<CountCostModel> countCosts, Long warehId, Long fsclUnitId) {
        return warehCostMapper.insertUpdateWarehCost(countCosts, warehId, fsclUnitId);
    }

    /**
     * 功能描述: 增加或修改库存成本
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/18 15:42
     */
    @Override
    public int addUpadteWarehCostList(List<WarehCost> warehCosts) {
        if (CollectionUtils.isNotEmpty(warehCosts)) {

            return warehCostMapper.addUpadteWarehCostList(warehCosts);
        }
        return 0;

    }
    /**
     * 修改仓库成本数量
     * @param warehStkList 商品Id、减少数量（已负）
     * @param warehId 仓库Id
     * @return 数据库操作数
     * @author HHe
     * @date 2019/11/22 11:36
     */
    @Override
    public int updateByWarehStk(List<WarehStk> warehStkList, Long warehId) {
        return warehCostMapper.updateByWarehStk(warehStkList,warehId);
    }
    /**
     * 减少数量
     * @author HHe
     * @date 2019/11/22 12:26
     */
    @Override
    public int updateByQty(List<CountCostModel> countCostModels, Long warehId) {
        return warehCostMapper.updateByQty(countCostModels,warehId);
    }
}
