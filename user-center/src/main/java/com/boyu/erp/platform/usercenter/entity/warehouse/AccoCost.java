package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * acco_cost
 * @author 
 */
@Data
public class AccoCost implements Serializable {
    /**
     * 组织Id
     */
    private Long unitId;

    /**
     * 商品品种Id
     */
    private Long prodClsId;
    /**
     * 单位成本
     */
    private BigDecimal unitCost;

    /**
     * 累积总价
     */
    private BigDecimal totVal;

    /**
     * 累积总数
     */
    private BigDecimal totQty;

    private static final long serialVersionUID = 1L;
}