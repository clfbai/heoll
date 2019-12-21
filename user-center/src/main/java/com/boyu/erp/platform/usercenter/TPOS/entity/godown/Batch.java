package com.boyu.erp.platform.usercenter.TPOS.entity.godown;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "batch")
public class Batch {
    /**
     * 批次编号
     */
    private String batchCode;
    /**
     * 生产日期,YYYY-MM-DD
     */
    private String productDate;
    /**
     * 过期日期,YYYY-MM-DD
     */
    private String expireDate;
    /**
     * 生产批号
     */
    private String produceCode;
    /**
     * 库存类型，ZP=正品,CC=残次,JS=机损,XS=箱损，默认为ZP,(收到商品总数=正品数+残品数+机损数+箱损数)
     */
    private String inventoryType;
    /**
     * 实收数量,int，要求batchs节点下所有的实收数量之和等于orderline中的实收数量
     */
    private String actualQty;
}
