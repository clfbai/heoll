package com.boyu.erp.platform.usercenter.mapper.basic;

import com.boyu.erp.platform.usercenter.entity.basic.Partner;

public interface PartnerMapper {
    int deleteByPrimaryKey(Partner key);

    int insert(Partner record);

    int insertSelective(Partner record);

    Partner selectByPrimaryKey(Partner key);

    int updateByPrimaryKeySelective(Partner record);

    int updateByPrimaryKey(Partner record);
}