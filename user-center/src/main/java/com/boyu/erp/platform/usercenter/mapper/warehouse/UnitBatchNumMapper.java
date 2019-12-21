package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatchNum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UnitBatchNumMapper {
    int deleteBatchNum(UnitBatchNum key);

    List<UnitBatchNum> getUnitBatch(UnitBatchNum key);

    int insertBatchNum(UnitBatchNum record);

    UnitBatchNum selectBatchNum(UnitBatchNum key);

    int updateBatchNum(UnitBatchNum record);

    /**
     * 查询顾客批次序号
     */
    UnitBatchNum getClinet(UnitBatchNum ub);

    /**
     * 功能描述: 查询组织可退商品数量与价格
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/9/23 18:50
     */
    List<UnitBatchNum> getUnitBatchNum(UnitBatchNum unitBatchNum);

    /**
     * 获取该商品可退的总数量
     *
     * @param unitBatchNum
     * @return
     */
    int getSumByUnitBatchNum(UnitBatchNum unitBatchNum);

    /**
     * 通过单据编号和组织查询对应所有批次
     */
    List<UnitBatchNum> selectDocNum(UnitBatchNum unitBatchNum);

    /**
     * 批量删除批次序号明细
     */
    int deleteBatchNumList(@Param("list") List<UnitBatchNum> revomeList);

    /**
     * 功能描述: 查询供应商批次集合
     *
     * @author CLF
     * @date: 2019/12/4 10:23
     */
    List<UnitBatchNum> selectUnitBatchNum(@Param("list") List<UnitBatchNum> venderBatchNum);

    /**
     * 修改批次序号集合(主要是取消出入库还原批次信息)
     */
    int updateBatchNumList(@Param("list") List<UnitBatchNum> updateList);

    /**
     * 查询采购商批次信息集合
     */
    List<UnitBatchNum> selectVendeeBatchNumList(@Param("list") List<Long> prodIdList, @Param("vendeeId") Long vendeeId);
}