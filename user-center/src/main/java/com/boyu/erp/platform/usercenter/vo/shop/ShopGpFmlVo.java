package com.boyu.erp.platform.usercenter.vo.shop;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ShopGpFmlVo implements Serializable {
    /**
     * 赠券发放公式
     */
    private String gcFml;

    /**
     * VIP折扣公式
     */
    private String vipDiscFml;

    /**
     * 积分累积公式
     */
    private String pntAccFml;

    /**
     * 积分兑现公式
     */
    private String pntEncFml;

    /**
     * 电子赠券发放公式
     */
    private String egcPsFml;

    /**
     * 备注
     */
    private String remarks;
    /**
     * 门店ID
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
