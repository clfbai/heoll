package com.boyu.erp.platform.usercenter.entity.collarUse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * arq_dtl
 * @author 
 */
@Data
@NoArgsConstructor
public class ArqDtl implements Serializable {
    /**
     * 组织id
     */
    private Long unitId;

    /**
     * 领用单编号
     */
    private String arqNum;

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
     * 数量
     */
    private BigDecimal qty;

    /**
     * 金额
     */
    private BigDecimal val;

    /**
     * 发货数量
     */
    private BigDecimal delivQty;

    /**
     * 发货金额
     */
    private BigDecimal delivVal;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;
}