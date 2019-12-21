package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehCost;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehStk;
import com.boyu.erp.platform.usercenter.model.wareh.CountCostModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface WarehCostMapper {
    int insert(WarehCost record);

    int insertSelective(WarehCost record);

    int updateByPrimaryKeySelective(WarehCost record);

    int updateByPrimaryKey(WarehCost record);
    /**
     * 查询仓库成本集合
     * @author HHe
     * @date 2019/10/25 10:47
     */
    List<WarehCost> queryWarehCostListByClsAndWareh(@Param("prodClsIds") Set<Long> prodClsIds, @Param("warehId") Long warehId);
    /**
     * 添加修改库存成本
     * @param countCosts 包含仓库仓库成本、仓库仓库数量、仓库商品金额
     * @param warehId 仓库Id
     * @param fsclUnitId 会计组织Id
     * @return 数据库执行数
     * @author HHe
     * @date 2019/11/14 10:05
     */
    int insertUpdateWarehCost(@Param("countCosts") List<CountCostModel> countCosts, @Param("warehId") Long warehId, @Param("fsclUnitId") Long fsclUnitId);


    int addUpadteWarehCostList(@Param("list")List<WarehCost> list);
    /**
     * 修改仓库成本数量
     * @param warehStkList 商品Id、减少数量（已负）
     * @param warehId 仓库Id
     * @return 数据库操作数
     * @author HHe
     * @date 2019/11/22 11:36
     */
    int updateByWarehStk(@Param("warehStkList") List<WarehStk> warehStkList, @Param("warehId") Long warehId);
    /**
     * 减少数量
     * @author HHe
     * @date 2019/11/22 12:27
     */
    int updateByQty(@Param("countCostModels") List<CountCostModel> countCostModels, @Param("warehId") Long warehId);
}