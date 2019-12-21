package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * wareh_stk (仓库库存表)
 *
 * @author
 */
@Data
@ToString
@NoArgsConstructor
public class WarehStk implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 仓库ID
     */
    private Long warehId;

    /**
     * 商品ID
     */
    private Long prodId;

    /**
     * 实际库存
     */
    private BigDecimal stkOnHand;

    /**
     * 预期库存
     */
    private BigDecimal qtyExpd;

    /**
     * 在途库存
     */
    private BigDecimal qtyInTran;

    /**
     * 预订库存
     */
    private BigDecimal qtyBkd;

    /**
     * 已配库存
     */
    private BigDecimal qtyCmtd;

    /**
     * 挂账库存
     */
    private BigDecimal qtyHldn;

    /**
     * 配码库存
     */
    private BigDecimal qtyAst;

    /**
     * 装箱库存
     */
    private BigDecimal qtyBxd;

    /**
     * 包装库存
     */
    private BigDecimal qtyPckd;


}