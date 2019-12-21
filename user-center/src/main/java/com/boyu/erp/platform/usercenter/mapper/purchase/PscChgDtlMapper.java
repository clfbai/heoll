package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.PscChgDtl;

import java.util.List;

public interface PscChgDtlMapper {
    int deleteByPrimaryKey(PscChgDtl key);

    int insert(PscChgDtl record);

    int insertSelective(PscChgDtl record);

    PscChgDtl selectByPrimaryKey(PscChgDtl key);

    int updateByPrimaryKeySelective(PscChgDtl record);

    int updateByPrimaryKey(PscChgDtl record);

    int insertList(List<PscChgDtl> list);
}