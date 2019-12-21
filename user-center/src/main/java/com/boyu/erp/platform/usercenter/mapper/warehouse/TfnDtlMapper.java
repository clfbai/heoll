package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.TfnDtl;
import com.boyu.erp.platform.usercenter.vo.warehouse.TfnDtlVo;

import java.util.List;

public interface TfnDtlMapper {
    int deleteByPrimaryKey(TfnDtl key);

    int insert(TfnDtl record);

    TfnDtl selectByPrimaryKey(TfnDtl key);

    int update(TfnDtl record);

    List<TfnDtlVo> getTfnDtlList(TfnDtl record);

}