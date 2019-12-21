package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * psa_dr
 * @author 
 */
@Data
public class PsaDr implements Serializable {

    /**
     * 供应商ID
     */
    private Long venderId;

    /**
     * 采购商ID
     */
    private Long vendeeId;

    /**
     * 协议控制方("r","e")
     */
    private String psaCtlr;

    /**
     * 购销合同类别
     */
    private String pscType;

    /**
     * 商品分类ID
     */
    private String prodCatId;

    /**
     * 单价下限
     */
    private BigDecimal minPrice;

    /**
     * 单价上限
     */
    private BigDecimal maxPrice;
    /**
     * 折率
     */
    private BigDecimal discRate;

    private static final long serialVersionUID = 1L;

}