package com.boyu.erp.platform.usercenter.TPOS.entity.godown.common;

import lombok.Data;

@Data
public class OrderCommon {
    /**
     * 外部业务编码,消息ID,用于去重，当单据需要分批次发送时使用
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
     * 商品编码,必填
     */
    private String itemCode;
    /**
     * 仓储系统商品ID,条件必填
     */
    private String itemId;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 库存类型，ZP=正品,CC=残次,JS=机损,XS=箱损，默认为ZP,(收到商品总数=正品数+残品数+机损数+箱损数)
     */
    private String inventoryType;
    /**
     * 应收应收商品数量 必填
     */
    private String planQty;
    /**
     * 批次编码
     */
    private String batchCode;
    /**
     * 商品生产日期，string（10），YYYY-MM-DD
     */
    private String productDate;
    /**
     * 商品过期日期，string（10），YYYY-MM-DD
     */
    private String expireDate;
    /**
     * 生产批号,
     */
    private String produceCode;
}
