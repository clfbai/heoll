package com.boyu.erp.platform.usercenter.entity.shop;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * shop_pay_mode
 *
 * @author
 */
@Data
@NoArgsConstructor
public class ShopPayMode implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 付款方式别名
     */
    private String payModeAlias;
    /**
     * 门店Id
     */
    private Long shopId;

    /**
     * 付款方式ID
     */
    private Integer payModeId;
}