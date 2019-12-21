package com.boyu.erp.platform.usercenter.entity.mq.order;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname OrderItems
 * @Description TODO
 * @Date 2019/10/9 11:55
 * @Created by wz
 */
@Data
@NoArgsConstructor
public class OrderItems implements Serializable {

    /**
     * 商品规格id
     */
    private String productSKUId;

    /**
     * 数量
     */
    private int quantity;

    /**
     * 吊牌价
     */
    private BigDecimal salePrice;

    /**
     * 折扣价
     */
    private BigDecimal discountAmount;

    /**
     * 实际应付金额
     */
    private BigDecimal actualAmount;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 颜色
     */
    private String color;

    /**
     * 尺码
     */
    private String size;

    /**
     * 杯型
     */
    private String cup;
}
