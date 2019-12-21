package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.Wareh;
import com.boyu.erp.platform.usercenter.entity.warehouse.WarehStk;
import com.boyu.erp.platform.usercenter.model.wareh.CountCostModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface WarehStkMapper {
    int deleteByWarehStk(WarehStk key);

    /**
     * 删除仓库时删除删除库存
     */
    int deleteByWarehStkWarehId(Long key);

    int insertWarehStk(WarehStk record);

    WarehStk selectByWarehStk(WarehStk key);

    /**
     * 查询仓库库存
     */
    List<WarehStk> selectWareh(Wareh w);

    /**
     * 查询仓库库存是否存在仓库记录
     */
    int selectCountWareh(Wareh w);

    int updateByWarehStk(WarehStk record);

    /**
     * 根据仓库Id和商品Id集合查询不为0的库存数量；
     *
     * @author HHe
     * @date 2019/9/19 12:01
     */
    List<WarehStk> queryWarehStkListByProdIdList(@Param("warehId") Long warehId, @Param("prodIdList") List<Long> prodIdList);

    /**
     * 根据仓库Id查询仓库所有实际库存非0的库存详情
     *
     * @author HHe
     * @date 2019/9/19 12:08
     */
    List<WarehStk> selectWarehByWarehIdIsNotZero(Wareh wareh);

    /**
     * 查询仓库中的商品集合
     *
     * @author HHe
     * @date 2019/10/21 11:28
     */
    List<WarehStk> queryWarehStkByProdsAndWareh(@Param("prodIds") Set<Long> prodIds, @Param("warehId") Long warehId);

    /**
     * 功能描述: 增加库存有重复记录则修改库存实际数量
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/12 19:52
     */
    int updateAddWarehStkList(@Param("list") List<WarehStk> list);
    /**
     * 添加库存信息如果存在则减去参数中的数量
     * @author HHe
     * @date 2019/11/22 9:42
     */
    int insertUpdateWarehStkList(@Param("countCostModels") List<CountCostModel> countCostModels, @Param("warehId") Long warehId);

}