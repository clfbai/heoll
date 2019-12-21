package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatch;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @program: ${PROJECT_NAME}
 * @description:组织批次
 * @author: CLF
 */
public interface UnitBatchMapper {
    int deleteUnitBatch(String batchId);

    int insertUnitBatch(UnitBatch record);

    UnitBatch selectUnitBatch(UnitBatch batchId);

    UnitBatch selectCilentUnitBatch(UnitBatch batch);

    int updateUnitBatch(UnitBatch record);

    /**
     * 根据商品ID、组织Id查询批次号
     */
    List<UnitBatch> getUnitBatch(UnitBatch u);

    /**
     * 根据商品ID查询批次号
     */
    UnitBatch countProdCut(UnitBatch u);

    int deleteUnitBatchList(@Param("list") List<UnitBatch> list);

    /**
     * 供应商批次信息集合(取消出入库是批次恢复)
     */
    List<UnitBatch> selectUnitBatchList(@Param("list") List<UnitBatch> venderBatch);

    /**
     * 查询批次集合
     *
     * @param prodIds 商品Id
     * @param unitId  组织Id
     * @return 批次信息集合
     * @author HHe
     * @date 2019/12/3 17:40
     */
    List<UnitBatch> queryBatchsByProdIdsAndUnit(@Param("prodIds") Set<Long> prodIds, @Param("unitId") Long unitId);

    int updateUnitBatchList(@Param("list") List<UnitBatch> updateBatch);

    List<UnitBatch> selectVendeeBatchList(List<Long> prodIdList, Long vendeeId);
}