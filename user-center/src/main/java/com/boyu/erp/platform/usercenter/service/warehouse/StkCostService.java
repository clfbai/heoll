package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.goods.Product;
import com.boyu.erp.platform.usercenter.entity.warehouse.StkCost;
import com.boyu.erp.platform.usercenter.model.wareh.CountCostModel;
import com.boyu.erp.platform.usercenter.model.wareh.StkCostModel;

import java.util.List;

/**
 * 库存成本
 *
 * @author HHe
 * @date 2019/8/28 10:48
 */
public interface StkCostService {
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
    List<StkCost> queryStkCostListByUnitproductLists(Long unitId, List<Product> productList);

    /**
     * 判断并核算成本
     * costChanged 是否影响成本
     * reversed 是否取反
     *
     * @author HHe
     * @date 2019/10/22 10:22
     */
    List<CountCostModel> instantCalculate(StkCostModel stkCostModel);


    /**
     * 功能描述: 根据会计组织Id和入库单明细生成需要修改组织成本对象
     * <p>
     * 会计组织 通过查询 仓库 和 仓库属主Id 在sys_unit_clf 表中的关系得到
     * 这里只需要   给StkCostModel 赋值 warehId 和 stbDtl这两个值
     * //todo 注意 stbDtl 集合是所有开启库存本商品的明细
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/14 15:09
     */
    List<StkCost> createStkCost(StkCostModel stkCostModel);

    /**
     * 功能描述: 存在组织成本修改不存在添加
     *
     * @param stkCost
     * @return:
     * @auther: CLF
     * @date: 2019/11/18 15:21
     */
    int addUpdateStkcost(List<StkCost> stkCost,Long unitId);

    /**
     * 添加修改库存成本（只修改成本）
     * @author HHe
     * @date 2019/11/22 11:19
     */
    int insertUpdateStkCost(List<CountCostModel> list, Long costGrpId, Long fsclUnitId);
    /**
     * 修改库存数量
     * @author HHe
     * @date 2019/11/22 12:30
     */
    int updateByQty(List<CountCostModel> countCostModels, Long fsclUnitId);
}
