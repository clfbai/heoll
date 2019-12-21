package com.boyu.erp.platform.usercenter.mapper;

import com.boyu.erp.platform.usercenter.entity.SpecScp;

import java.util.List;

/**
 * 规格范围
 */

public interface SpecScpMapper {

    public List<SpecScp> selectAll(SpecScp specScp);

    public SpecScp selectByPrimaryKey(SpecScp specScp);

    public int updateSpecScp(SpecScp specScp);

    public int insertSpecScp(SpecScp specScp);

    public int deleteSpecScp(SpecScp specScp);
    List<SpecScp> selectOption();
}