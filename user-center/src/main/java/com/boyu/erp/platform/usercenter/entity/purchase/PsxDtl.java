package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * psx_dtl
 * @author 
 */
@Data
@NoArgsConstructor
public class PsxDtl implements Serializable {
    /**
     * 购销申请号
     */
    private String psxNum;

    /**
     * 商品ID
     */
    private Long prodId;
    /**
     * 行号
     */
    private Long lineNum;

    /**
     * 排号
     */
    private Long rowNum;

    /**
     * 货期
     */
    private Date reqdDate;

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
     * 市场单价
     */
    private BigDecimal mkUnitPrice;

    /**
     * 数量
     */
    private BigDecimal qty;

    /**
     * 金额
     */
    private BigDecimal val;

    /**
     * 市值
     */
    private BigDecimal mkv;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;

}