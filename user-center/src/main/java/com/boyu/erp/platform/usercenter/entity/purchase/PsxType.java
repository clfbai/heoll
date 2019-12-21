package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;

import java.io.Serializable;

/**
 * psx_type
 * @author 
 */
@Data
public class PsxType implements Serializable {
    /**
     * 购销申请类别
     */
    private String psxType;

    /**
     * 描述
     */
    private String description;

    /**
     * 须指定采购商("t","f")
     */
    private String vdeReqd;

    /**
     * 须指定供应商("t","f")
     */
    private String vdrReqd;

    /**
     * 须指定收货仓库("t","f")
     */
    private String rcvWarehReqd;

    /**
     * 采购申请自动生成("t","f")
     */
    private String puaAutoGen;

    /**
     * 采购申请自动审核("t","f")
     */
    private String puaAutoChk;

    /**
     * 须指定发货仓库("t","f")
     */
    private String delivWarehReqd;

    /**
     * 销售申请自动生成("t","f")
     */
    private String slaAutoGen;

    /**
     * 销售申请自动审核("t","f")
     */
    private String slaAutoChk;

    /**
     * 货期控制("sg","pd","SalesOrder")
     */
    private String rqdCtrl;

    /**
     * 货期控制可选("t","f")
     */
    private String rqdCtrlAlt;

    /**
     * 启用配码("t","f")
     */
    private String bxiEnabled;

    /**
     * 启用配码可选("t","f")
     */
    private String bxiEnabledAlt;

    /**
     * 购销合同类别
     */
    private String pscType;

    /**
     * 商品分类ID
     */
    private String prodCatId;

    private static final long serialVersionUID = 1L;

}