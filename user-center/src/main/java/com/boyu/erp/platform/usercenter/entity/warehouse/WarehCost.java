package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * wareh_cost
 *
 * @author
 */
@Data
public class WarehCost implements Serializable {
    /**
     * 仓库Id
     */
    private Long warehId;

    /**
     * 商品品种Id
     */
    private Long prodClsId;

    /**
     * 属主Id
     */
    private Long ownerId;

    /**
     * 仓库商品成本(单价)
     */
    private BigDecimal warehCost;

    /**
     * 总数量
     */
    private BigDecimal totQty;

    /**
     * 总成本价格(总金额)
     */
    private BigDecimal totVal;

    /**
     * 未决金额
     */
    private BigDecimal pgVal;

    private static final long serialVersionUID = 1L;
}