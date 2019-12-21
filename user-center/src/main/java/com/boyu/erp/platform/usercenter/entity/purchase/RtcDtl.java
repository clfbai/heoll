package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * rtc_dtl
 * @author 
 */
@Data
@NoArgsConstructor
public class RtcDtl implements Serializable {
    /**
     * 退货合同号
     */
    private String rtcNum;

    /**
     * 商品ID
     */
    private Long prodId;
    /**
     * 行号
     */
    private Long lineNum;

    /**
     * 排号
     */
    private Long rowNum;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 折率
     */
    private BigDecimal discRate;

    /**
     * 折后价
     */
    private BigDecimal fnlPrice;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 市场单价
     */
    private BigDecimal mkUnitPrice;

    /**
     * 数量
     */
    private BigDecimal qty;

    /**
     * 金额
     */
    private BigDecimal val;

    /**
     * 税款
     */
    private BigDecimal tax;

    /**
     * 市值
     */
    private BigDecimal mkv;

    /**
     * 发货数量
     */
    private BigDecimal delivQty;

    /**
     * 发货金额
     */
    private BigDecimal delivVal;

    /**
     * 发货税款
     */
    private BigDecimal delivTax;

    /**
     * 发货市值
     */
    private BigDecimal delivMkv;

    /**
     * 到货数量
     */
    private BigDecimal rcvQty;

    /**
     * 到货金额
     */
    private BigDecimal rcvVal;

    /**
     * 到货税款
     */
    private BigDecimal rcvTax;

    /**
     * 到货市值
     */
    private BigDecimal rcvMkv;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;

}