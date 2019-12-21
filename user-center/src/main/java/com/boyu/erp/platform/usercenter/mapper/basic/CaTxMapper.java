package com.boyu.erp.platform.usercenter.mapper.basic;

import com.boyu.erp.platform.usercenter.entity.basic.CaTx;

public interface CaTxMapper {
    int deleteByPrimaryKey(CaTx key);

    int insert(CaTx record);

    int insertSelective(CaTx record);

    CaTx selectByPrimaryKey(CaTx key);

    int updateByPrimaryKeySelective(CaTx record);

    int updateByPrimaryKey(CaTx record);
}