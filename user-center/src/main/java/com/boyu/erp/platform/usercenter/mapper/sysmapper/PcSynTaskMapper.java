package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.PcSynTask;

public interface PcSynTaskMapper {
    int deleteByPrimaryKey(PcSynTask key);

    int insert(PcSynTask record);

    int insertSelective(PcSynTask record);

    PcSynTask selectByPrimaryKey(PcSynTask key);

    int updateByPrimaryKeySelective(PcSynTask record);

    int updateByPrimaryKey(PcSynTask record);
}