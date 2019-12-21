package com.boyu.erp.platform.usercenter.entity.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 可退未决金额额度
 * rtv_qta_pg
 * @author 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RtvQtaPg implements Serializable {
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
     * 冻结金额
     */
    private BigDecimal frzVal;

    private static final long serialVersionUID = 1L;

}