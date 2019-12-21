package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.PsxDtlRqd;
import com.boyu.erp.platform.usercenter.vo.purchase.PsxDtlVo;

import java.util.List;

public interface PsxDtlRqdMapper {
    int deleteByPrimaryKey(PsxDtlRqd key);

    int insert(PsxDtlRqd record);

    int insertSelective(PsxDtlRqd record);

    PsxDtlRqd selectByPrimaryKey(PsxDtlRqd key);

    int updateByPrimaryKeySelective(PsxDtlRqd record);

    int updateByPrimaryKey(PsxDtlRqd record);

    int updateByReqdDate(PsxDtlRqd record);

    int deleteByRqdCtrl(String psxNum);

    /**
     * 批量增加货期数据
     * @param rqdList
     * @return
     */
    int insertList(List<PsxDtlRqd> rqdList);

    /**
     * 批量更新货期数据
     */
    int updateList(List<PsxDtlVo> list);

    /**
     * 批量删除明细时，批量删除明细货期
     */
    int deleteList(List<PsxDtlVo> list);
}