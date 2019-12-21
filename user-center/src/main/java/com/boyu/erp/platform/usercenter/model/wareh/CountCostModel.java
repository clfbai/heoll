package com.boyu.erp.platform.usercenter.model.wareh;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 核算成本model
 * @author HHe
 * @date 2019/10/22 20:39
 */
@Data
public class CountCostModel implements Serializable {
    /**
     * 组织Id
     */
    private Long unitId;
    /**
     * 仓库Id
     */
    private Long warehId;
    /**
     * 税款
     */
    private BigDecimal tax;
    /**
     * 商品Id
     */
    private Long prodId;
    /**
     * 商品品种Id
     */
    private Long prodClsId;
    /**
     * 数量
     */
    private BigDecimal qty;
    /**
     * 金额
     */
    private BigDecimal val;
    /**
     * 商品仓库成本
     */
    private BigDecimal warehCost;
    /**
     * 商品仓库数量
     */
    private BigDecimal warehQty;
    /**
     * 商品仓库金额
     */
    private BigDecimal warehVal;
    /**
     * 仓库未决金额
     */
    private BigDecimal warehPgVal;
    /**
     * 商品组织成本
     */
    private BigDecimal unitCost;
    /**
     * 商品组织数量
     */
    private BigDecimal unitQty;
    /**
     * 组织未决金额（用于核算时，除数为0的情况）
     */
    private BigDecimal unitPgVal;
}
