package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 类描述: stb_doc_dtl (库存单原始单据明细)
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/7/31 10:28
 */
@Data
@NoArgsConstructor
public class StbDocDtl implements Serializable {
    private static final long serialVersionUID = 1L;
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
     * 预期数量
     */
    private BigDecimal expdQty;

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
     * 未决数量
     */
    private BigDecimal pgQty;

    /**
     * 分货数量
     */
    private BigDecimal cltQty;

    /**
     * 复核数量
     */
    private BigDecimal rckQty;

    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 库存单编号
     */
    private String stbNum;

    /**
     * 原始单据类别
     */
    private String srcDocType;

    /**
     * 原始单据组织ID
     */
    private Integer srcDocUnitId;

    /**
     * 原始单据编号
     */
    private String srcDocNum;

    /**
     * 商品ID
     */
    private Long prodId;


}