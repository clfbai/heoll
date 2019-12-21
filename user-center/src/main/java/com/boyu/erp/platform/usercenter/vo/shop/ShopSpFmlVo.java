package com.boyu.erp.platform.usercenter.vo.shop;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
public class ShopSpFmlVo implements Serializable {
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
    private Date fromDateCp;

    /**
     * 结束日期
     */
    private Date toDateCp;

}
