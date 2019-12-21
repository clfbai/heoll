package com.boyu.erp.platform.usercenter.mapper.collarUse;

import com.boyu.erp.platform.usercenter.entity.collarUse.ArqDtl;

public interface ArqDtlMapper {
    int deleteByPrimaryKey(ArqDtl record);

    int insert(ArqDtl record);

    int insertSelective(ArqDtl record);

    ArqDtl selectByPrimaryKey(ArqDtl record);

    int updateByPrimaryKeySelective(ArqDtl record);

    int updateByPrimaryKey(ArqDtl record);
}