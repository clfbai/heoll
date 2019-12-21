package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.PsaTr;
import com.boyu.erp.platform.usercenter.vo.purchase.PsaRateVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsaVo;

import java.util.List;

public interface PsaTrMapper {
    int deleteByPrimaryKey(PsaTr key);

    int insert(PsaTr record);

    int insertSelective(PsaTr record);

    PsaTr selectByPrimaryKey(PsaTr key);

    int updateByPrimaryKeySelective(PsaTr record);

    int updateByPrimaryKey(PsaTr record);

    List<PsaRateVo> selectByPsa(PsaRateVo vo);
    //查询是否有记录
    List<PsaRateVo> selectByPsaTr(PsaRateVo vo);

    int insertByPsa(PsaRateVo vo);

    int updateByPsa(PsaRateVo vo);

    //删除单条折率记录
    int deleteByPsaTr(PsaRateVo vo);

    int deleteByPsa(PsaVo psaVo);
}