package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.TfnType;

import java.util.List;

public interface TfnTypeMapper {
    int deleteByTfnType(String tfnType);

    int insertTfnType(TfnType record);

    TfnType selectByTfnType(String tfnType);

    int updateByTfnType(TfnType record);

    /**
     * 获得所有调拨单类别
     */
    List<TfnType> getAll(TfnType record);
}