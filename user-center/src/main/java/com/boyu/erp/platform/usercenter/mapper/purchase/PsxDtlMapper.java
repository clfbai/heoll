package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.PsoDtl;
import com.boyu.erp.platform.usercenter.entity.purchase.PsxDtl;
import com.boyu.erp.platform.usercenter.vo.purchase.PsxDtlVo;

import java.util.List;
import java.util.Map;

public interface PsxDtlMapper {
    int deleteByPrimaryKey(PsxDtl key);

    int insert(PsxDtl record);

    int insertSelective(PsxDtl record);

    PsxDtl selectByPrimaryKey(PsxDtl key);

    int updateByPrimaryKeySelective(PsxDtl record);

    int updateByPrimaryKey(PsxDtl record);

    List<PsxDtlVo> findByPsxNum(PsxDtlVo vo);

    PsxDtl selectByDesc(String psxNum);

    int insertByPsxNum(PsxDtlVo p);

    int updateByPsxNum(PsxDtlVo p);

    int deleteByPsxNum(String psxNum);

    int updateByRqdCtrl(Map<String, Object> map);

    /**
     * 批量新增明细
     * @param list
     * @return
     */
    int insertList(List<PsxDtlVo> list);

    /**
     * 批量删除明细
     */
    int deleteList(List<PsxDtlVo> list);

    /**
     * 批量更新明细
     */
    int updateList(List<PsxDtlVo> list);
}