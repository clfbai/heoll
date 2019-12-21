package com.boyu.erp.platform.usercenter.mapper;

import com.boyu.erp.platform.usercenter.entity.SatDtl;
import com.boyu.erp.platform.usercenter.entity.SatDtlKey;
import com.boyu.erp.platform.usercenter.vo.SatDtlVo;

import java.util.List;

/**
 * 配码明细
 */
public interface SatDtlMapper {

    public List<SatDtlVo> selectAll(SatDtlVo satDtl);

    public  SatDtl selectByPrimaryKey(SatDtlKey key);

    public int insertSelective(SatDtl record);

    public int updateByPrimaryKeySelective(SatDtl record);

    public int deleteByPrimaryKey(SatDtlKey key);

}