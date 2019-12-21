package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.BatchRefNum;

public interface BatchRefNumMapper {
    int deleteBatchRefNum(Long id);

    int insertBatchRefNum(BatchRefNum record);

    BatchRefNum selectBatchRefNum(Long id);

    int updateBatchRefNum(BatchRefNum record);

    BatchRefNum  getBatchRefNum(BatchRefNum  b);

}