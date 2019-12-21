package com.boyu.erp.platform.usercenter.service.purchaseservice;

import com.boyu.erp.platform.usercenter.vo.purchase.PrcVo;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcDtlVoByBatch;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Classname RtcDtlService
 * @Description TODO
 * @Date 2019/7/26 9:53
 * @Created wz
 */
public interface RtcDtlService {

    /**
     * 分页查询
     * @param vo
     * @return
     */
    List<RtcDtlVo> findByRtcNum(RtcDtlVo vo);

    /**
     * 新增
     * @param vo
     * @return
     */
    int insertRtcDtl(RtcDtlVo vo);

    /**
     * 修改
     * @param vo
     * @return
     */
    int updateRtcDtl(RtcDtlVo vo);

    /**
     * 删除
     * @param v
     * @return
     */
    int deleteRtcDtl(RtcDtlVo v);

    /**
     * 重算主表数据
     * @param v
     * @return
     */
    PrcVo insertByPrc(PrcVo v);

    /**
     * 退购明细输入数量后更新数据
     * @param model
     * @return
     */
    RtcDtlVo updatePrcDtl(RtcDtlVoByBatch model);

    /**
     * 查询可退总金额
     * @param prodId
     * @param vendeeId
     * @param venderId
     * @param qty
     * @return
     */
    BigDecimal Calculation(Long prodId, Long vendeeId, Long venderId, int qty);

}
