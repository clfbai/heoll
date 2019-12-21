package com.boyu.erp.platform.usercenter.mapper.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.StbBox;

import java.util.List;

public interface StbBoxMapper {
    int deleteByPrimaryKey(StbBox key);

    int insert(StbBox record);

    StbBox selectByPrimaryKey(StbBox key);

    int updateByPrimaryKey(StbBox record);

    List<StbBox> getStbBoxList(StbBox record);
}