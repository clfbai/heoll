package com.boyu.erp.platform.usercenter.mapper.purchase;

import com.boyu.erp.platform.usercenter.entity.purchase.Rtc;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcVo;

public interface RtcMapper {
    int deleteByPrimaryKey(String rtcNum);

    int insert(Rtc record);

    int insertSelective(Rtc record);

    Rtc selectByPrimaryKey(String rtcNum);

    int updateByPrimaryKeySelective(Rtc record);

    int updateByPrimaryKey(Rtc record);

    int insertByRtcVo(PrcVo vo);

    int updateByPrcVo(PrcVo vo);

    int updeteByGen(String prcGen, String srcGen,String rtcNum);

    //删除所有明细时更新相关数据
    int updateByRtcDtl(String rtcNum);

    /**
     * 功能操作中更新状态
     * @param vo
     * @return
     */
    int updateBystate(PrcVo vo);

    /**
     * 通过原始单据查询退货合同
     * @param rtc
     * @return
     */
    Rtc selectBySrcDoc(Rtc rtc);
}