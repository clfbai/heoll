package com.boyu.erp.platform.usercenter.mapper;

import com.boyu.erp.platform.usercenter.entity.basic.TranDiffEvt;

public interface TranDiffEvtMapper {
    int deleteByPrimaryKey(TranDiffEvt key);

    int insert(TranDiffEvt record);

    int insertSelective(TranDiffEvt record);

    TranDiffEvt selectByPrimaryKey(TranDiffEvt key);

    int updateByPrimaryKeySelective(TranDiffEvt record);

    int updateByPrimaryKey(TranDiffEvt record);

    /**
     * 查询不符合的记录
     * @param tran
     * @return
     */
    TranDiffEvt selectByError(TranDiffEvt tran);
}