package com.boyu.erp.platform.usercenter.model.wareh;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 成本中间对象
 * @author HHe
 * @date 2019/10/25 11:17
 */
@Data
public class CommCostModel implements Serializable {
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
}

