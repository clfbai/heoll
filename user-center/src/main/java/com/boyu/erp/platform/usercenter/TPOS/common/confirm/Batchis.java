package com.boyu.erp.platform.usercenter.TPOS.common.confirm;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@ToString
@NoArgsConstructor
@XmlRootElement(name = "batch")
public class Batchis {
    /**
     * 批次编号
     */
    protected String batchCode;
    /**
     * 生产日期，string(10)，YYYY-MM-DD
     */
    protected String productDate;
    /**
     * 过期日期，string(10)，YYYY-MM-DD
     */
    protected String expireDate;
    /**
     * 生产批号
     */
    protected String produceCode;
    /**
     * 库存类型
     */
    protected String inventoryType;
    /**
     * 批次数量
     */
    protected String actualQty;
}
