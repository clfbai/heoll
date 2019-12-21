package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehStkLmt;

public interface WarehStkLmtMapper {
    int deleteByPrimaryKey(WarehStkLmt key);

    int insert(WarehStkLmt record);

    int insertSelective(WarehStkLmt record);

    WarehStkLmt selectByPrimaryKey(WarehStkLmt key);

    int updateByPrimaryKeySelective(WarehStkLmt record);

    int updateByPrimaryKey(WarehStkLmt record);
}