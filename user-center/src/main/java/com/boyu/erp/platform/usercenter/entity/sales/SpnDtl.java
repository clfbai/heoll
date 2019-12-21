package com.boyu.erp.platform.usercenter.entity.sales;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * spn_dtl
 * @author 
 */
@Data
@NoArgsConstructor
public class SpnDtl implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 价格单编号
     */
    private String spnNum;

    /**
     * 商品品种ID
     */
    private Long prodClsId;
    /**
     * 行号
     */
    private Long lineNum;

    /**
     * 定价策略
     */
    private String prcPlcy;

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
     * 特价
     */
    private String specOfr;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;

}