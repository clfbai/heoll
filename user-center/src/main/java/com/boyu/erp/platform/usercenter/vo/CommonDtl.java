package com.boyu.erp.platform.usercenter.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 明细父类
 */
@Data
public class CommonDtl implements Serializable {
    /**
     * 商品ID
     */
    private Long prodId;

    /**
     * 商品品种id
     */
    private Long prodClsId;

    /**
     * 商品库存管理
     */
    private String stkAdopted;

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
     * 市场单价
     */
    private BigDecimal mkUnitPrice;

    /**
     * 市值
     */
    private BigDecimal mkv;

    /**
     * 备注
     */
    private String remarks;
}
