package com.boyu.erp.platform.usercenter.entity.mq.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname SrcOrderItems
 * @Description TODO
 * @Date 2019/12/3 16:20
 * @Created by wz
 */
@Data
public class SrcOrderItems implements Serializable {

    /**
     * 订单中退货商品skuid
     */
    private String productSKUId;

    /**
     * 申请退货数量
     */
    private double applyQuantity;

    /**
     * 退货均价
     */
    private BigDecimal price;
}
