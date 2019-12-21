package com.boyu.erp.platform.usercenter.TPOS.common.confirm;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@ToString
@NoArgsConstructor
@XmlRootElement(name = "orderLine")
public class FirmOrderLine {
    /**
     * 外部业务编码, 消息ID, 用于去重，当单据需要分批次发送时使用
     */
    protected String outBizCode;
    /**
     * 单据行号，string（50）
     */
    protected String orderLineNo;


    /**
     * 货主代码(单据属主代码)
     */
    protected String ownerCode;
    /**
     * 商品编码 not null  (erp中prod_code)
     */
    protected String itemCode;
    /**
     * 仓储系统商品ID条件必填
     */
    protected String itemId;
    /**
     * 商品名称,
     */
    protected String itemName;
    /**
     * 库存类型 ZP=正品,CC=残次,JS=机损,XS=箱损，默认为ZP,(收到商品总数=正品数+残品数+机损数+箱损数)
     */
    protected String inventoryType;
    /**
     * 应收数量
     */
    protected Integer planQty;
    /**
     * 实收数量,必填
     */
    protected Integer actualQty;
    /**
     * 批次编码
     */
    protected String batchCode;
    /**
     * 商品生产日期，YYYY-MM-DD
     */
    protected String productDate;
    /**
     * 商品过期日期，string（10），YYYY-MM-DD
     */
    protected String expireDate;
    /**
     * 生产批号,
     */
    protected String produceCode;
    /**
     * 备注
     */
    protected String remark;

    protected Batchs batchs;


}
