package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.Cha;
import com.boyu.erp.platform.usercenter.vo.purchase.ChaVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsaVo;

public interface ChaMapper {

    int insert(Cha record);

    int insertSelective(Cha record);

    Cha selectByPrimaryKey(Cha key);

    int updateByPrimaryKey(Cha record);

    ChaVo selectByPsa(PsaVo psaVo);

    int insertByPsa(PsaVo psaVo);

    int deleteByPsa(PsaVo psaVo);

    int updateByPsa(PsaVo psaVo);
}