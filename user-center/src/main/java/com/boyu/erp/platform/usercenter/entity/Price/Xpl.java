package com.boyu.erp.platform.usercenter.entity.Price;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * xpl
 * @author 
 */
@Data
@NoArgsConstructor
public class Xpl implements Serializable {
    /**
     * 供应商ID
     */
    private Long venderId;

    /**
     * 采购商ID
     */
    private Long vendeeId;

    /**
     * 价格控制方
     */
    private String prcCtlr;

    /**
     * 价格类别
     */
    private String xpType;

    /**
     * 商品品种ID
     */
    private Long prodClsId;
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
     * 失效日期
     */
    private Date expdDate;

    private static final long serialVersionUID = 1L;

}