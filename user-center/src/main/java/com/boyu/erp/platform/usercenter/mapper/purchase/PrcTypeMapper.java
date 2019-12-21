package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.PrcType;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcAutoVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcTypeVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;

import java.util.List;

public interface PrcTypeMapper {
    int deletePrcType(PrcType prcType);

    int insertPrcType(PrcType prcType);

    int updatePrcType(PrcType prcType);

    public List<PrcTypeVo> selectALL(PrcType prcType);

    public List<PrcTypeVo> selectOneRtc(PrcType prcType);

    List<PurKeyValue> optionGet();

    RtcAutoVo selectByRtcAuto(String prcType);

    PrcType selectByRtcType(String rtcType);

    List<PrcType> listByPrcType(String prcType, String rtcType);

    List<PrcType> listByRtcType(String prcType, String rtcType);
}