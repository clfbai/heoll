package com.boyu.erp.platform.usercenter.vo.shop;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
public class ShopRentValVo implements Serializable {
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
    private String fromDateCp;

    /**
     * 结束日期
     */
    private String toDateCp;
}
