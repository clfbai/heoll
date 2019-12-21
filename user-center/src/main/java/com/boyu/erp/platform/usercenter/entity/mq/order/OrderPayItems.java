package com.boyu.erp.platform.usercenter.entity.mq.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname OrderPayItems
 * @Description TODO
 * @Date 2019/10/9 11:59
 * @Created by wz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayItems implements Serializable {

    /**
     * 支付代码
     * （MallPay->商城支付；MallCoupon->商城优惠劵支付；ErpPay->ERP支付；ErpCoupon->ERP优惠支付；Other->其他支付）
     */
    private String payCode;

    /**
     * 支付金额
     */
    private BigDecimal amount;
}
