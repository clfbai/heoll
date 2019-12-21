package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * wareh_stk_lmt  仓库库存预警
 * @author 
 */
@Data
@NoArgsConstructor
public class WarehStkLmt  implements Serializable {
    /**
     * 仓库ID
     */
    private Long warehId;

    /**
     * 商品ID
     */
    private Long prodId;
    /**
     * 库存上限
     */
    private BigDecimal maxStk;

    /**
     * 库存下限
     */
    private BigDecimal minStk;

    /**
     * 最佳库存
     */
    private BigDecimal bestStk;

    private static final long serialVersionUID = 1L;

}