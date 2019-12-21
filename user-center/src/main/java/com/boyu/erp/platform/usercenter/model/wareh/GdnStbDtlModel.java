package com.boyu.erp.platform.usercenter.model.wareh;

import lombok.Data;

import java.io.Serializable;

/**
 * 出库单明细模板
 * @author HHe
 * @date 2019/11/29 17:21
 */
@Data
public class GdnStbDtlModel implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 库存单编号
     */
    private String stbNum;

    /**
     * 商品ID
     */
    private Long prodId;
    /**
     * 单价
     */
    private String unitPrice;

    /**
     * 折率
     */
    private String discRate;

    /**
     * 折后价
     */
    private String fnlPrice;

    /**
     * 税率
     */
    private String taxRate;

    /**
     * 数量
     */
    private String qty;

    /**
     * 备注
     */
    private String remarks;
}
