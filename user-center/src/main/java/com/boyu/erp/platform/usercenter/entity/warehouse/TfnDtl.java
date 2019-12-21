package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * tfn_dtl  (调拨单明细表)
 * @author 
 */
@Data
@ToString
@NoArgsConstructor
public class TfnDtl implements Serializable {
    /**
     * 组织ID
     */
    private long unitId;

    /**
     * 调拨单号
     */
    private String tfnNum;

    /**
     * 商品ID
     */
    private long prodId;
    /**
     * 排号
     */
    private Integer rowNum;

    /**
     * 行号
     */
    private Integer lineNum;

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
}