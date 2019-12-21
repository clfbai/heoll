package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehStk;
import com.boyu.erp.platform.usercenter.model.wareh.CountCostModel;

import java.util.List;
import java.util.Set;

/**
 * 仓库库存服务
 *
 * @author HHe
 * @date 2019/9/3 10:50
 */
public interface WarehStkService {
    /**
     * 根据仓库id、商品Id查询仓库库存信息（待完善）
     *
     * @author HHe
     * @date 2019/9/3 12:19
     */
    WarehStk queryWarehStkByWarehIdProdId(Long warehId, Long prodId);

    /**
     * 根据仓库Id查询仓库所有库存详情
     *
     * @author HHe
     * @date 2019/9/18 19:31
     */
    List<WarehStk> queryWarehStkListByWarehId(Long warehId);

    /**
     * 根据仓库Id和商品Id集合查询不为0的库存数量；
     *
     * @author HHe
     * @date 2019/9/19 11:54
     */
    List<WarehStk> queryWarehStkListByProdIdList(Long warehId, List<Long> prodIdList);

    /**
     * 查询仓库中的商品集合
     *
     * @author HHe
     * @date 2019/10/21 11:26
     */
    List<WarehStk> queryWarehStkByProdsAndWareh(Set<Long> prodIds, Long warehId);

    /**
     * 功能描述: 增加库存数量
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/12 19:50
     */
    int updateAddWarehStkList(List<WarehStk> warehStkList);
    /**
     * 添加库存信息如果存在则减去参数中的数量
     * @author HHe
     * @date 2019/11/22 9:42
     */
    int insertUpdateWarehStkList(List<CountCostModel> countCostModels, Long warehId);
}
