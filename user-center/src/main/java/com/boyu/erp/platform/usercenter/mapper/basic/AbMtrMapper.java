package com.boyu.erp.platform.usercenter.mapper.basic;

import com.boyu.erp.platform.usercenter.entity.basic.AbMtrKey;

public interface AbMtrMapper {
    int deleteByPrimaryKey(AbMtrKey key);

    int insert(AbMtrKey record);

    int insertSelective(AbMtrKey record);

    AbMtrKey selectByAbMtrKey(AbMtrKey record);
}