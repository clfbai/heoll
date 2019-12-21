package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * psc_dtl
 * @author 
 */
@Data
public class PscDtl implements Serializable {
    /**
     * 购销合同号
     */
    private String pscNum;

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
     * 货期
     */
    private Date reqdDate;

    /**
     * 可退率
     */
    private BigDecimal rtnaRate;

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
     * 退销数量
     */
    private BigDecimal rsQty;

    /**
     * 退销金额
     */
    private BigDecimal rsVal;

    /**
     * 退销税款
     */
    private BigDecimal rsTax;

    /**
     * 退销市值
     */
    private BigDecimal rsMkv;

    /**
     * 退购数量
     */
    private BigDecimal rpQty;

    /**
     * 退购金额
     */
    private BigDecimal rpVal;

    /**
     * 退购税款
     */
    private BigDecimal rpTax;

    /**
     * 退购市值
     */
    private BigDecimal rpMkv;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;

    public PscDtl() {
    }

    public PscDtl(String pscNum, Long prodId) {
        this.pscNum = pscNum;
        this.prodId = prodId;
    }
}