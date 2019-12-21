package com.boyu.erp.platform.usercenter.TPOS.entity.delivery;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * WMS出库单确认明细
 * @author 
 */
@Data
public class WStkoCnDtl implements Serializable {
    /**
     * 订单Id
     */
    private String id;

    /**
     * 商品ID
     */
    private Integer prodId;

    /**
     * 外部业务编码
     */
    private String obizCode;

    /**
     * 单据行号
     */
    private String ordLineNo;

    /**
     * 商品编码
     */
    private String itemCode;

    /**
     * 仓储系统商品ID
     */
    private String itemId;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 库存类型
     */
    private String stkType;

    /**
     * 实际数量
     */
    private BigDecimal actQty;

    /**
     * 批次编码
     */
    private String batchCode;

    /**
     * 生产批号
     */
    private String prdcCode;

    /**
     * 商品生产日期
     */
    private String prodDate;

    /**
     * 商品过期日期
     */
    private String expdDate;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;
}