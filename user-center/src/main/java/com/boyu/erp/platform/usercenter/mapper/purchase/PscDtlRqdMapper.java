package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.PscDtlRqd;

import java.util.List;

public interface PscDtlRqdMapper {
    int deleteByPrimaryKey(PscDtlRqd key);

    int insert(PscDtlRqd record);

    int insertSelective(PscDtlRqd record);

    PscDtlRqd selectByPrimaryKey(PscDtlRqd key);

    int updateByPrimaryKeySelective(PscDtlRqd record);

    int updateByPrimaryKey(PscDtlRqd record);

    int updateByReqdDate(PscDtlRqd record);

    int deleteByRqdCtrl(String pscNum);

    List<PscDtlRqd> selectByPscNum(String pscNum);

    /**
     * 明细中新增集合数据
     * @param list
     * @return
     */
    int insertList(List<PscDtlRqd> list);

    /**
     * 明细中删除集合数据
     * @return
     */
    int deleteList(List<PscDtlRqd> list);

    /**
     * 明细中更新集合数据
     * @return
     */
    int updateList(List<PscDtlRqd> list);
}