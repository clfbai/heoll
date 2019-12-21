package com.boyu.erp.platform.usercenter.mapper.shop;

import com.boyu.erp.platform.usercenter.entity.shop.CurrType;

import java.util.List;

public interface CurrTypeMapper {
    int deleteByPrimaryKey(Integer currId);

    int insert(CurrType record);

    int insertSelective(CurrType record);

    CurrType selectByPrimaryKey(Integer currId);

    int updateByPrimaryKeySelective(CurrType record);

    int updateByPrimaryKey(CurrType record);

    List<CurrType> getCurrTyrp(CurrType currType);
}