package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatch;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatchNum;
import com.boyu.erp.platform.usercenter.model.batch.BatchModel;
import com.boyu.erp.platform.usercenter.model.batch.WarehBatchModel;

import java.util.List;
import java.util.Set;

/**
 * @program: boyu-platform_text
 * @description: 批次接口
 * @author: clf
 * @create: 2019-07-05 09:31
 */
public interface UnitBatchSerivce {
    /**
     * 增加组织批次
     */
    int addUnitBatch(BatchModel u,String docType, SysUser user) throws Exception;





    /**
     * 查询商品是否有批次号
     */
    UnitBatch selectProdcut(UnitBatch u);

    /**
     * 功能描述:  查询组织可退商品数量与价格
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/23 18:46
     */
    List<UnitBatchNum> getUnitBatchNum(UnitBatchNum unitBatchNum);

    /**
     * 查询组织可退商品总数量
     *
     * @param unitBatchNum
     * @return
     */
    int getSumByUnitBatchNum(UnitBatchNum unitBatchNum);

    /**
     *
     * 功能描述:
     *
     * @param warehBatchModel (采购商  -1 为顾客)
     * @return:
     * @auther: CLF
     * @date: 2019/11/20 10:05
     */
   List<BatchModel>  createBatch(WarehBatchModel warehBatchModel)throws Exception;
    /**
     * 查询批次集合
     * @param prodIds 商品Id
     * @param unitId 组织Id
     * @return 批次信息集合
     * @author HHe
     * @date 2019/12/3 17:40
     */
    List<UnitBatch> queryBatchsByProdIdsAndUnit(Set<Long> prodIds, Long unitId);
}
