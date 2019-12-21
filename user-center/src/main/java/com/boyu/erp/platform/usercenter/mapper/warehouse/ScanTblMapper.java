package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.ScanTbl;

public interface ScanTblMapper {
    int deleteById(String scanTblId);

    int insertScanTbl(ScanTbl record);

    ScanTbl selectById(String scanTblId);

    int updateScanTbl(ScanTbl record);


}