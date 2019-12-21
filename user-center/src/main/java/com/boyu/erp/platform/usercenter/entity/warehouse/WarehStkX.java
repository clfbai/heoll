package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * wareh_stk_x
 * @author 
 */
@Data
public class WarehStkX implements Serializable {
    /**
     * 快照时间
     */
    private Timestamp snptTime;

    /**
     * 仓库ID
     */
    private Long warehId;

    /**
     * 商品ID
     */
    private Long prodId;
    /**
     * 实际库
     */
    private BigDecimal stkOnHand;
}