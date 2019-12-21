package com.boyu.erp.platform.usercenter.entity.basic;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 可退未决数量额度
 * rtq_qta_pg
 * @author 
 */
@Data
@NoArgsConstructor
public class RtqQtaPg implements Serializable {
    /**
     * 供应商ID
     */
    private Long venderId;

    /**
     * 采购商ID
     */
    private Long vendeeId;

    /**
     * 协议控制方
     */
    private String psaCtlr;

    /**
     * 商品品种ID
     */
    private Long prodClsId;

    /**
     * 单据类别
     */
    private String docType;

    /**
     * 单据组织ID
     */
    private Long docUnitId;

    /**
     * 单据编号
     */
    private String docNum;
    /**
     * 冻结额度
     */
    private BigDecimal frzQty;

    private static final long serialVersionUID = 1L;

}