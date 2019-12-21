package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * san_dtl
 * @author 
 */
@Data
public class SanDtl implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 调整单编号
     */
    private String sanNum;

    /**
     * 商品ID
     */
    private Long prodId;

    /**
     * 货位ID
     */
    private Long locId;

    /**
     * 行号
     */
    private Integer lineNum;

    /**
     * 排号
     */
    private Integer rowNum;

    /**
     * 数量
     */
    private BigDecimal qty;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;
}