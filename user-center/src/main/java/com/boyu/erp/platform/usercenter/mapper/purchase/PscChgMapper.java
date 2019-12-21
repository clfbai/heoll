package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.PscChg;

public interface PscChgMapper {
    int deleteByPrimaryKey(PscChg key);

    int insert(PscChg record);

    int insertSelective(PscChg record);

    PscChg selectByPrimaryKey(PscChg key);

    int updateByPrimaryKeySelective(PscChg record);

    int updateByPrimaryKey(PscChg record);

    PscChg selectByPscDtl(String pscNum);
}