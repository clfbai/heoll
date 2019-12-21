package com.boyu.erp.platform.usercenter.entity.basic;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * rtv_qta
 * 可退金额额度
 * @author 
 */
@Data
public class RtvQta implements Serializable {
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
     * 可退金额
     */
    private BigDecimal rtvQta;

    /**
     * 调整额度
     */
    private BigDecimal adjQta;

    /**
     * 已用金额
     */
    private BigDecimal usedVal;

    /**
     * 冻结金额
     */
    private BigDecimal frzVal;

    private static final long serialVersionUID = 1L;

    public RtvQta() {
    }

    public RtvQta(Long venderId, Long vendeeId, String psaCtlr) {
        this.venderId = venderId;
        this.vendeeId = vendeeId;
        this.psaCtlr = psaCtlr;
    }
}