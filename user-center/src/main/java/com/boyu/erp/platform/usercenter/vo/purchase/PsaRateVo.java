package com.boyu.erp.platform.usercenter.vo.purchase;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname PsaRateVo
 * @Description TODO
 * @Date 2019/6/21 15:29
 * @Created wz  采购协议中折率 可退率 税率
 */
@Data
public class PsaRateVo implements Serializable {
    /**
     * 供应商ID
     */
    private String venderId;

    /**
     * 采购商ID
     */
    private String vendeeId;

    /**
     * 协议控制方("r","e")
     */
    private String psaCtlr;

    /**
     * 购销合同类别
     */
    private String pscType;

    /**
     * 商品分类id
     */
    private String prodCatId;

    /**
     * 商品分类名称
     */
    private String prodCatName;

    /**
     * 折率/可退率/税率
     */
    private String discRate;

    /**
     * 单价下限
     */
    private String minPrice;

    /**
     * 单价上限
     */
    private String maxPrice;

    /**
     * 修改后的购销合同类别
     */
    private String upPscType;

}
