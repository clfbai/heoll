package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * tfn_bxi (调拨单配码表)
 *
 * @author
 */
@Data
@ToString
@NoArgsConstructor
public class TfnBxi implements Serializable {
    /**
     * 组织ID
     */
    private long unitId;

    /**
     * 调拨单号
     */
    private String tfnNum;

    /**
     * 商品品种ID
     */
    private long prodClsId;

    /**
     * 颜色ID
     */
    private long colorId;

    /**
     * 版型
     */
    private String edition;

    /**
     * 特征串
     */
    private String egnStr;

    /**
     * 行号
     */
    private Integer lineNum;

    /**
     * 配码ID
     */
    private Short satId;

    /**
     * 每箱数量
     */
    private BigDecimal unitQty;

    /**
     * 箱数
     */
    private Integer box;

    /**
     * 发货箱数
     */
    private Integer delivBox;

    /**
     * 到货箱数
     */
    private Integer rcvBox;

    /**
     * 备注
     */
    private String remarks;
}