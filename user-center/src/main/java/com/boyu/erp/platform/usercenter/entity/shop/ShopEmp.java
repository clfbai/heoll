package com.boyu.erp.platform.usercenter.entity.shop;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * shop_emp 门店员工表
 *
 * @author
 */
@Data
@NoArgsConstructor
public class ShopEmp implements Serializable {
    /**
     * 最低零售折扣
     */
    private BigDecimal minRtDiscRate;

    /**
     * 简码
     */
    private String smpdCode;
    /**
     * 门店ID
     */
    private Long shopId;

    /**
     * 员工ID
     */
    private Long emplId;

    private static final long serialVersionUID = 1L;


}