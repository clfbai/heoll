package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * psc_type
 * @author 
 */
@Data
public class PscType implements Serializable {
    /**
     * 购销合同类别
     */
    private String pscType;

    /**
     * 描述
     */
    private String description;

    /**
     * 须指定收货仓库("t","f")
     */
    private String rcvWarehReqd;

    /**
     * 采购合同自动生成("t","f")
     */
    private String pucAutoGen;

    /**
     * 采购合同自动审核("t","f")
     */
    private String pucAutoChk;

    /**
     * 是否居间采购("t","f")
     */
    private String isPuItmd;

    /**
     * 是否居间采购可选("t","f")
     */
    private String isPuItmdAlt;

    /**
     * 须指定发货仓库("t","f")
     */
    private String delivWarehReqd;

    /**
     * 销售合同自动生成("t","f")
     */
    private String slcAutoGen;

    /**
     * 销售合同自动审核("t","f")
     */
    private String slcAutoChk;

    /**
     * 是否居间销售("t","f")
     */
    private String isSlItmd;

    /**
     * 是否居间销售可选("t","f")
     */
    private String isSlItmdAlt;

    /**
     * 须指定中转方("t","f")
     */
    private String tranUnitReqd;

    /**
     * 差异裁定方("d","r")
     */
    private String drDiffJgd;

    /**
     * 差异裁定方可选("t","f")
     */
    private String drDiffJgdAlt;

    /**
     * 按指令执行("t","f")
     */
    private String implByIns;

    /**
     * 按指令执行可选("t","f")
     */
    private String implByInsAlt;

    /**
     * 多次执行("t","f")
     */
    private String multiImpl;

    /**
     * 多次执行可选("t","f")
     */
    private String multiImplAlt;

    /**
     * 是否现货("t","f")
     */
    private String isSpot;

    /**
     * 是否现货可选("t","f")
     */
    private String isSpotAlt;

    /**
     * 定价点("ct","dl","rc")
     */
    private String prcSite;

    /**
     * 定价点可选("t","f")
     */
    private String prcSiteAlt;

    /**
     * 固定单价("t","f")
     */
    private String fixedUnitPrice;

    /**
     * 货期控制("sg","pd","SalesOrder")
     */
    private String rqdCtrl;

    /**
     * 货期控制可选("t","f")
     */
    private String rqdCtrlAlt;

    /**
     * 允许增补商品("t","f")
     */
    private String splEnabled;

    /**
     * 允许增补商品可选("t","f")
     */
    private String splEnabledAlt;

    /**
     * 超额许可比例
     */
    private BigDecimal exblRate;

    /**
     * 是否代销("t","f")
     */
    private String isCms;

    /**
     * 是否代销可选("t","f")
     */
    private String isCmsAlt;

    /**
     * 结算方式("dp","pa","pp")
     */
    private String psStlMthd;

    /**
     * 结算方式可选("t","f")
     */
    private String psStlMthdAlt;

    /**
     * 启用冻款("t","f")
     */
    private String mfzEnabled;

    /**
     * 启用冻款可选("t","f")
     */
    private String mfzEnabledAlt;

    /**
     * 冻款点("ck","ad")
     */
    private String psMfzSite;

    /**
     * 冻款点可选("t","f")
     */
    private String psMfzSiteAlt;

    /**
     * 定金比例
     */
    private BigDecimal dpstRate;

    /**
     * 启用保证金("t","f")
     */
    private String gmEnabled;

    /**
     * 启用保证金可选("t","f")
     */
    private String gmEnabledAlt;

    /**
     * 保证金比例
     */
    private BigDecimal gmRate;

    /**
     * 计划控制("t","f")
     */
    private String planCtrl;

    /**
     * 计划控制可选("t","f")
     */
    private String planCtrlAlt;

    /**
     * 启用配码("t","f")
     */
    private String bxiEnabled;

    /**
     * 启用配码可选("t","f")
     */
    private String bxiEnabledAlt;

    /**
     * 商品分类ID
     */
    private String prodCatId;

    private static final long serialVersionUID = 1L;

}