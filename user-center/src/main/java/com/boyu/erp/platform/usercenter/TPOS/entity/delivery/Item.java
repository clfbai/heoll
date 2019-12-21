package com.boyu.erp.platform.usercenter.TPOS.entity.delivery;

import lombok.Data;

@Data
public class Item {
    /**
     * 商品编码
     */
    private String itemCode;
    /**
     * 商品仓储系统编码
     */
    private String itemId;
    /**
     * 包裹内该商品的数量
     */
    private int quantity;
}
