package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.Tfn;
import com.boyu.erp.platform.usercenter.vo.warehouse.TfnVo;

import java.util.List;

public interface TfnMapper {
    int deleteByPrimaryKey(Tfn key);

    int insert(Tfn record);

    int update(Tfn record);

    List<TfnVo> getTfnList(Tfn record);

    TfnVo getTfnVoByPk(Tfn record);

    Tfn getTfnByPk(Tfn record);

    String getMaxTfnNum(Tfn record);
}