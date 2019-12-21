package com.boyu.erp.platform.usercenter.mapper.basic;

import com.boyu.erp.platform.usercenter.entity.basic.AbPf;

public interface AbPfMapper {
    int deleteByPrimaryKey(Long unitId);

    int insert(AbPf record);

    int insertSelective(AbPf record);

    AbPf selectByPrimaryKey(Long unitId);

    int updateByPrimaryKeySelective(AbPf record);

    int updateByPrimaryKey(AbPf record);
}