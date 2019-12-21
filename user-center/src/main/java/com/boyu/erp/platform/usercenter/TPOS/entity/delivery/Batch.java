package com.boyu.erp.platform.usercenter.TPOS.entity.delivery;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class Batch {
    /**
     * 批次编号
     */
    private String batchCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    /**
     * 生产日期
     */
    private String productDate;
    /**
     * 过期日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String expireDate;
    /**
     * 生产批号
     */
    private String produceCode;
    /**
     * 库存类型
     */
    private String inventoryType;
    /**
     * 实发数量
     */
    private String actualQty;
}
