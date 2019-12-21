package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * rtc_type
 * @author 
 */
@Data
public class RtcType implements Serializable {
    /**
     * 退货合同类别
     */
    private String rtcType;

    /**
     * 描述
     */
    private String description;

    /**
     * 关联购销合同("t","f")
     */
    private String pscReqd;

    /**
     * 须指定发货仓库("t","f")
     */
    private String delivWarehReqd;

    /**
     * 退购合同自动生成("t","f")
     */
    private String prcAutoGen;

    /**
     * 退购合同自动审核("t","f")
     */
    private String prcAutoChk;

    /**
     * 是否居间退购("t","f")
     */
    private String isPrItmd;

    /**
     * 是否居间退购可选("t","f")
     */
    private String isPrItmdAlt;

    /**
     * 须指定收货仓库("t","f")
     */
    private String rcvWarehReqd;

    /**
     * 退销合同自动生成("t","f")
     */
    private String srcAutoGen;

    /**
     * 退销合同自动审核("t","f")
     */
    private String srcAutoChk;

    /**
     * 是否居间退销("t","f")
     */
    private String isSrItmd;

    /**
     * 是否居间退销可选("t","f")
     */
    private String isSrItmdAlt;

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
     * 多次执行("t","f")
     */
    private String multiImpl;

    /**
     * 多次执行可选("t","f")
     */
    private String multiImplAlt;

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
     * 占用可退额度("t","f")
     */
    private String rtnaInvd;

    /**
     * 占用可退额度可选("t","f")
     */
    private String rtnaInvdAlt;

    /**
     * 启用授信("t","f")
     */
    private String accEnabled;

    /**
     * 启用授信可选("t","f")
     */
    private String accEnabledAlt;

    /**
     * 授信点("ck","dd")
     */
    private String rtnAccSite;

    /**
     * 授信点可选("t","f")
     */
    private String rtnAccSiteAlt;

    /**
     * 授信比例
     */
    private BigDecimal accRate;

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