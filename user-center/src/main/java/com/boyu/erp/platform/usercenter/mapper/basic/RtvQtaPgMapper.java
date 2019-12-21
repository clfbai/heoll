package com.boyu.erp.platform.usercenter.mapper.basic;

import com.boyu.erp.platform.usercenter.entity.basic.RtvQtaPg;

public interface RtvQtaPgMapper {
    int deleteByPrimaryKey(RtvQtaPg key);

    int insert(RtvQtaPg record);

    int insertSelective(RtvQtaPg record);

    RtvQtaPg selectByPrimaryKey(RtvQtaPg key);

    int updateByPrimaryKeySelective(RtvQtaPg record);

    int updateByPrimaryKey(RtvQtaPg record);
}