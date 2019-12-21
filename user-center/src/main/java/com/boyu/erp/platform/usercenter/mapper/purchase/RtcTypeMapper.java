package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.RtcType;
import com.boyu.erp.platform.usercenter.vo.partner.RtcTypeVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;

import java.util.List;

public interface RtcTypeMapper {
    int deleteByPrimaryKey(String rtcType);

    int insert(RtcType record);

    int insertSelective(RtcType record);

    RtcType selectByPrimaryKey(String rtcType);

    int updateByPrimaryKeySelective(RtcType record);

    int updateByPrimaryKey(RtcType record);

    List<PurKeyValue> optionGet();

    List<RtcTypeVo> selectALL(RtcType rtcType);
}