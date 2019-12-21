package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * pkn_dtl
 * @author 
 */
@Data
public class PknDtl implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 分拣单编号
     */
    private String pknNum;

    /**
     * 行号
     */
    private Integer lineNum;

    /**
     * 排号
     */
    private Integer rowNum;

    /**
     * 商品ID
     */
    private Long prodId;

    /**
     * 货位ID
     */
    private Long locId;

    /**
     * 分配数量
     */
    private BigDecimal allocQty;

    /**
     * 实际数量
     */
    private BigDecimal actQty;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;

}