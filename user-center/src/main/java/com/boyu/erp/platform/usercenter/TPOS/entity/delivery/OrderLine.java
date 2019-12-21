package com.boyu.erp.platform.usercenter.TPOS.entity.delivery;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderLine{

    /**
     * 外部业务编码
     */
    private String outBizCode;
    /**
     * 单据行号
     */
    private String orderLineNo;
    /**
     * 货主编码 必填
     */
    private String ownerCode;
    /**
     * 商品编码 必填
     */
    private String itemCode;
    /**
     * 仓储系统商品编码 条件必填
     */
    private String itemId;
    /**
     * 商品Id
     */
    private Long prodId;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 库存类型
     */
    private String inventoryType;
    /**
     * 应发商品数量 必填
     */
    private int planQty;
    /**
     * 商品单价
     */
    private BigDecimal price;
    /**
     * 批次编码
     */
    private String batchCode;
    /**
     * 商品生产日期 YYYY-MM-DD
     */
    private String productDate;
    /**
     * 商品过期日期YYYY-MM-DD
     */
    private Date expireDate;
    /**
     * 生产批号
     */
    private String produceCode;

    private Batchs batchs;

}
