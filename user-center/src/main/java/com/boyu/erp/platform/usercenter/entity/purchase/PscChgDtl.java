package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * psc_chg_dtl
 * @author 
 */
@Data
public class PscChgDtl implements Serializable {
    /**
     * 购销合同号
     */
    private String pscNum;

    /**
     * 记录号
     */
    private Long rcdNum;

    /**
     * 商品ID
     */
    private Long prodId;
    /**
     * 调整数量
     */
    private BigDecimal adjQty;

    private static final long serialVersionUID = 1L;

}