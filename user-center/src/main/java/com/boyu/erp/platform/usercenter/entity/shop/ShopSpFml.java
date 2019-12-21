package com.boyu.erp.platform.usercenter.entity.shop;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * shop_sp_fml 门店扣点公式 表
 *
 * @author
 */
@Data
@NoArgsConstructor
public class ShopSpFml implements Serializable {
    /**
     * 扣点公式
     */
    private String spFml;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 门店ID
     */
    private Long shopId;

    /**
     * 付款方式ID
     */
    private Integer payModeId;

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
    private static final long serialVersionUID = 1L;

}