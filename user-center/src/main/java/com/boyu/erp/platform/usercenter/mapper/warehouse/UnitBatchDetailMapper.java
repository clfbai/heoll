package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatchDetail;
/**
*@program: ${PROJECT_NAME}
*@description:批次操作明细
*@author: CLF
*/
public interface UnitBatchDetailMapper {
    int deleteBatchDetail(Long id);

    int insertBatchDetail(UnitBatchDetail record);

    UnitBatchDetail selectBatchDetail(Long id);

    int updateBatchDetail(UnitBatchDetail record);


}