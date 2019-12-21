package com.boyu.erp.platform.usercenter.mapper.sales;

import com.boyu.erp.platform.usercenter.entity.sales.RgoType;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;

import java.util.List;

public interface RgoTypeMapper {
    int deleteByRgoType(RgoType record);

    int insert(RgoType record);

    int updateByRgoType(RgoType record);

    List<RgoType> getRgoTypeList(RgoType record);

    //下拉查询
    List<PurKeyValue> optionGet();

    /**
     *
     * @param rgoType
     * @return
     */
    RgoType selectByRgoType(String rgoType);
}