package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.StbDocDtl;

public interface StbDocDtMapper {
    int deleteByPrimaryKey(StbDocDtl key);

    int insert(StbDocDtl record);

    int insertSelective(StbDocDtl record);

    StbDocDtl selectByPrimaryKey(StbDocDtl key);

    int updateByPrimaryKeySelective(StbDocDtl record);

    int updateByPrimaryKey(StbDocDtl record);
}