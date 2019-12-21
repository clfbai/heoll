package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.warehouse.StkCost;
import com.boyu.erp.platform.usercenter.model.wareh.CountCostModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface StkCostMapper {

    int insert(StkCost record);

    int insertSelective(StkCost record);

    int updateByPrimaryKeySelective(StkCost record);

    int updateByPrimaryKey(StkCost record);

    /**
     * 根据组织和品种id查询成本
     *
     * @author HHe
     * @date 2019/8/28 10:59
     */
    StkCost queryStkCostByUnitCls(StkCost stkCost);

    /**
     * 根据组织Id和商品信息集合的品种信息查询成本集合
     *
     * @author HHe
     * @date 2019/9/5 19:58
     */
    List<StkCost> queryStkCostListByUnitproductLists(@Param("unitId") Long unitId, @Param("productList") List<Product> productList);

    /**
     * 查询库存成本集合
     *
     * @param prodClsList 品种集合
     * @param fsclUnitId  会计组织Id
     * @return 库存成本集合
     * @author HHe
     * @date 2019/11/12 17:51
     */
    List<StkCost> queryListByWarehAndClsList(@Param("prodClsList") Set<Long> prodClsList, @Param("fsclUnitId") Long fsclUnitId);

    /**
     * 添加库存成本，若主键存在则修改其中字段
     *
     * @param countCosts 包含品种、组织成本的集合
     * @param costGrpId  成本组Id
     * @param fsclUnitId 会计组织
     * @return
     * @author HHe
     * @date 2019/11/14 9:43
     */
    int insertUpdateStkCost(@Param("countCosts") List<CountCostModel> countCosts, @Param("costGrpId") Long costGrpId, @Param("fsclUnitId") Long fsclUnitId);

    /**
     * 存在修改不存再添加
     */
    int insertUpdateStkCostList(@Param("list") List<StkCost> list);
    /**
     * 修改库存数量
     * @author HHe
     * @date 2019/11/22 12:32
     */
    int updateByQty(@Param("countCostModels") List<CountCostModel> countCostModels, @Param("fsclUnitId") Long fsclUnitId);
}