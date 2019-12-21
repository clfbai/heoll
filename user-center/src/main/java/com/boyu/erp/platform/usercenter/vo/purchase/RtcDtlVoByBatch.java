package com.boyu.erp.platform.usercenter.vo.purchase;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname RtcDtlVoByBatch
 * @Description TODO
 * @Date 2019/9/25 17:36
 * @Created by wz
 */
@Data
@AllArgsConstructor
public class RtcDtlVoByBatch implements Serializable {
    //采购商
    private Long vendeeId;
    //供应商
    private Long venderId;
    //输入数量
    private int qty;
    //退货商品详情
    private List<RtcDtlVo> vo;
}
