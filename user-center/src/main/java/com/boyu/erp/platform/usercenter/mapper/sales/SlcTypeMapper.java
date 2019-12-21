package com.boyu.erp.platform.usercenter.mapper.sales;

import com.boyu.erp.platform.usercenter.entity.sales.SlcType;
import com.boyu.erp.platform.usercenter.vo.purchase.PscAutoVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;

import java.util.List;

public interface SlcTypeMapper {
    int deleteBySlcType(SlcType slcType);

    int insert(SlcType record);

    int updateBySlcType(SlcType record);

    List<SlcType> getSlcTypeList(SlcType slcType);

    SlcType selecyByPscType(String pscType);

    PscAutoVo selectByPscAuto(String slcType);

    List<SlcType> listBySlcType(String slcType,String pscType);

    List<SlcType> listByPscType(String slcType,String pscType);

    List<PurKeyValue> optionGet();
}