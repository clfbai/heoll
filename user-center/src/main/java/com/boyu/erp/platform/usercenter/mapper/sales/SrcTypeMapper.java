package com.boyu.erp.platform.usercenter.mapper.sales;

import com.boyu.erp.platform.usercenter.entity.sales.SrcType;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcAutoVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;

import java.util.List;

public interface SrcTypeMapper {
    int deleteBySrcType(SrcType record);

    int insert(SrcType record);

    int updateBySrcType(SrcType record);

    List<SrcType> getSrcTypeList(SrcType record);

    SrcType selectByRtcType(String rtcType);

    List<PurKeyValue> optionGet();

    RtcAutoVo selectByRtcAuto(String srcType);

    List<SrcType> listBySrcType(String srcType,String rtcType);

    List<SrcType> listByRtcType(String srcType,String rtcType);
}