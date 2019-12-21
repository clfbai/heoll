package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.Psx;
import com.boyu.erp.platform.usercenter.vo.purchase.PsxVo;

public interface PsxMapper {
    int deleteByPrimaryKey(String psxNum);

    int insert(Psx record);

    int insertByPsxVo(PsxVo vo);

    Psx selectByPrimaryKey(String psxNum);

    int updateByPuaVo(PsxVo vo);

    int updateByPrimaryKey(Psx record);

    int updateByPrimaryKeySelective(Psx record);

    /**
     * 更新个别字段状态
     * @param puaGen
     * @param slaGen
     * @param psxNum
     * @return
     */
    int updeteByGen(String puaGen,String slaGen,String psxNum);

    /**
     * 功能中更新状态
     * @param vo
     * @return
     */
    int updateByPsxVo(PsxVo vo);
}