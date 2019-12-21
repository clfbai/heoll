package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.PsaRtr;
import com.boyu.erp.platform.usercenter.vo.purchase.PsaRateVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsaVo;

import java.util.List;

public interface PsaRtrMapper {
    int deleteByPrimaryKey(PsaRtr key);

    int insert(PsaRtr record);

    int insertSelective(PsaRtr record);

    PsaRtr selectByPrimaryKey(PsaRtr key);

    int updateByPrimaryKeySelective(PsaRtr record);

    int updateByPrimaryKey(PsaRtr record);

    List<PsaRateVo> selectByPsa(PsaRateVo vo);
    //查询是否有记录
    List<PsaRateVo> selectByPsaRtr(PsaRateVo vo);

    int insertByPsa(PsaRateVo vo);

    int updateByPsa(PsaRateVo vo);

    //删除单条折率记录
    int deleteByPsaRtr(PsaRateVo vo);

    int deleteByPsa(PsaVo psaVo);
}