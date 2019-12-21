package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.TfnBxi;
import com.boyu.erp.platform.usercenter.vo.warehouse.TfnBxiVo;

import java.util.List;

public interface TfnBxiMapper {
    int insert(TfnBxi record);

    int insertSelective(TfnBxi record);

    int delete(TfnBxi record);

    int update(TfnBxi record);

    List<TfnBxiVo> getTfnBxiList(TfnBxi record);
}