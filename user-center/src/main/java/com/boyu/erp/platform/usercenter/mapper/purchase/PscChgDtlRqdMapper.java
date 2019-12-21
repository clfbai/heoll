package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.PscChgDtlRqd;

import java.util.List;

public interface PscChgDtlRqdMapper {
    int deleteByPrimaryKey(PscChgDtlRqd key);

    int insert(PscChgDtlRqd record);

    int insertSelective(PscChgDtlRqd record);

    PscChgDtlRqd selectByPrimaryKey(PscChgDtlRqd key);

    int updateByPrimaryKeySelective(PscChgDtlRqd record);

    int updateByPrimaryKey(PscChgDtlRqd record);

    int insertList(List<PscChgDtlRqd> list);
}