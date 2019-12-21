package com.boyu.erp.platform.usercenter.entity.basic;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 可退数量额度
 * rtq_qta
 * @author 
 */
@Data
public class RtqQta implements Serializable {
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
     * 可退数量
     */
    private BigDecimal rtqQta;

    /**
     * 调整额度
     */
    private BigDecimal adjQta;

    /**
     * 已用数量
     */
    private BigDecimal usedQty;

    /**
     * 冻结数量
     */
    private BigDecimal frzQty;

    /**
     * 失效日期
     */
    private Date expdDate;

    private static final long serialVersionUID = 1L;

    public RtqQta() {
    }

    public RtqQta(Long venderId, Long vendeeId, String psaCtlr, Long prodClsId) {
        this.venderId = venderId;
        this.vendeeId = vendeeId;
        this.psaCtlr = psaCtlr;
        this.prodClsId = prodClsId;
    }
}