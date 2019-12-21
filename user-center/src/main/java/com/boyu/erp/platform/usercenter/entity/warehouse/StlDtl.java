package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * stl_dtl
 * @author 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StlDtl implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 清单编号
     */
    private String stlNum;

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
     * 数量
     */
    private BigDecimal qty;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;
}