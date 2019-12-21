package com.boyu.erp.platform.usercenter.entity.shop;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * shop_rent_val 门店租金
 *
 * @author
 */
@Data
@NoArgsConstructor
public class ShopRentVal  implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 租金
     */
    private Float rentVal;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 门店Id
     */
    private Long shopId;

    /**
     * 开始日期
     */
    private Date fromDate;

    /**
     * 结束日期
     */
    private Date toDate;
    /**
     * 开始日期String
     */
    private String fromDateCp;

    /**
     * 结束日期String
     */
    private String toDateCp;
}