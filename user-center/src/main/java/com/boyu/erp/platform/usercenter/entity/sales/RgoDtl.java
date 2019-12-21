package com.boyu.erp.platform.usercenter.entity.sales;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * rgo_dtl
 * @author 
 */
@Data
public class RgoDtl implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 调配单号
     */
    private String rgoNum;

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
     * 调出单价
     */
    private BigDecimal puUnitPrice;

    /**
     * 调出折率
     */
    private BigDecimal puDiscRate;

    /**
     * 调出折后价
     */
    private BigDecimal puFnlPrice;

    /**
     * 调出税率
     */
    private BigDecimal puTaxRate;

    /**
     * 调出零售单价
     */
    private BigDecimal puMkUnitPrice;

    /**
     * 调入单价
     */
    private BigDecimal slUnitPrice;

    /**
     * 调入折率
     */
    private BigDecimal slDiscRate;

    /**
     * 调入折后价
     */
    private BigDecimal slFnlPrice;

    /**
     * 调入税率
     */
    private BigDecimal slTaxRate;

    /**
     * 调入零售单价
     */
    private BigDecimal slMkUnitPrice;

    /**
     * 调入可退率
     */
    private BigDecimal slRtnaRate;

    /**
     * 数量
     */
    private BigDecimal qty;

    /**
     * 调出金额
     */
    private BigDecimal puVal;

    /**
     * 调出税款
     */
    private BigDecimal puTax;

    /**
     * 调出市值
     */
    private BigDecimal puMkv;

    /**
     * 调入金额
     */
    private BigDecimal slVal;

    /**
     * 调入税款
     */
    private BigDecimal slTax;

    /**
     * 调入市值
     */
    private BigDecimal slMkv;

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

    public RgoDtl() {
    }

    public RgoDtl(Long unitId, String rgoNum) {
        this.unitId = unitId;
        this.rgoNum = rgoNum;
    }

    public RgoDtl(Long unitId, String rgoNum, Long prodId) {
        this.unitId = unitId;
        this.rgoNum = rgoNum;
        this.prodId = prodId;
    }
}