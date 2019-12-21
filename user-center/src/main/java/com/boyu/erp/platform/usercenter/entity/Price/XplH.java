package com.boyu.erp.platform.usercenter.entity.Price;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * xpl_h
 * @author 
 */
@Data
@NoArgsConstructor
public class XplH implements Serializable {
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
     * 组织ID
     */
    private Long unitId;

    /**
     * 价格单编号
     */
    private String xpnNum;
    /**
     * 定价操作
     */
    private String prcOpr;

    /**
     * 执行时间
     */
    private Date execTime;

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