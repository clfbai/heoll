package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.StbDoc;

public interface StbDocMapper {
    int deleteByPrimaryKey(StbDoc key);

    int insert(StbDoc record);

    int insertSelective(StbDoc record);
}