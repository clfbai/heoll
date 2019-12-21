package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * stk_cost  组织成本表
 *
 * @author
 */
@Data
public class StkCost implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 成本组ID
     */
    private Long costGrpId;

    /**
     * 商品品种ID
     */
    private Long prodClsId;
    /**
     * 单位成本
     */
    private BigDecimal unitCost;

    /**
     * 未决金额
     */
    private BigDecimal pgVal;

    /**
     * 参考成本
     */
    private BigDecimal refUnitCost;

    /**
     * 总数量
     */
    private BigDecimal totQty;

    public StkCost(Long unitId, Long prodClsId) {
        this.unitId = unitId;
        this.prodClsId = prodClsId;
    }

    public StkCost() {
    }
}