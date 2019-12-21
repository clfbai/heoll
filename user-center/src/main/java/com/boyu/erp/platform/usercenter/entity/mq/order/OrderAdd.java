package com.boyu.erp.platform.usercenter.entity.mq.order;

import com.boyu.erp.platform.usercenter.entity.mq.product.ProductSku;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname OrderAdd
 * @Description TODO
 * @Date 2019/10/9 11:14
 * @Created by wz
 */
@Data
@NoArgsConstructor
public class OrderAdd implements Serializable {

    /**
     * 订单id
     */
    private String id;

    /**
     * 消费发生的门店id
     */
    private String shopId;

    /**
     * 订单来源
     */
    private int orderSource;

    /**
     * 订单类型
     */
    private int orderType;

    /**
     * 供货商/品牌商id
     */
    private String supplierId;

    /**
     * 订单商品
     */
    private List<OrderItemsToB> items = new ArrayList<>();

}
