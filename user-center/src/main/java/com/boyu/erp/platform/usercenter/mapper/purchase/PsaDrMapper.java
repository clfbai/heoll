package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.PsaDr;
import com.boyu.erp.platform.usercenter.vo.purchase.PsaRateVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsaVo;

import java.util.List;

public interface PsaDrMapper {
    int deleteByPrimaryKey(PsaDr key);

    int insert(PsaDr record);

    int insertSelective(PsaDr record);

    PsaDr selectByPrimaryKey(PsaDr key);

    int updateByPrimaryKeySelective(PsaDr record);

    int updateByPrimaryKey(PsaDr record);

    List<PsaRateVo> selectByPsa(PsaRateVo vo);
    //查询是否有记录
    List<PsaRateVo> selectByPsaDr(PsaRateVo vo);

    int insertByPsa(PsaRateVo vo);

    int updateByPsa(PsaRateVo vo);
    //删除单条折率记录
    int deleteByPsaDr(PsaRateVo vo);

    int deleteByPsa(PsaVo psaVo);
}