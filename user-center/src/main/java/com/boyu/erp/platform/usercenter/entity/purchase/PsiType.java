package com.boyu.erp.platform.usercenter.entity.purchase;

import java.io.Serializable;

/**
 * psi_type
 * @author 
 */
public class PsiType implements Serializable {
    /**
     * 购销意向类别
     */
    private String psiType;

    /**
     * 描述
     */
    private String description;

    /**
     * 须指定收货仓库("t","f")
     */
    private String rcvWarehReqd;

    /**
     * 采购意向自动生成("t","f")
     */
    private String puiAutoGen;

    /**
     * 采购意向自动审核("t","f")
     */
    private String puiAutoChk;

    /**
     * 须指定发货仓库("t","f")
     */
    private String delivWarehReqd;

    /**
     * 销售意向自动生成("t","f")
     */
    private String sliAutoGen;

    /**
     * 销售意向自动审核("t","f")
     */
    private String sliAutoChk;

    /**
     * 须指定中转方("t","f")
     */
    private String tranUnitReqd;

    /**
     * 关联展会("t","f")
     */
    private String tfReqd;

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

    public String getPsiType() {
        return psiType;
    }

    public void setPsiType(String psiType) {
        this.psiType = psiType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRcvWarehReqd() {
        return rcvWarehReqd;
    }

    public void setRcvWarehReqd(String rcvWarehReqd) {
        this.rcvWarehReqd = rcvWarehReqd;
    }

    public String getPuiAutoGen() {
        return puiAutoGen;
    }

    public void setPuiAutoGen(String puiAutoGen) {
        this.puiAutoGen = puiAutoGen;
    }

    public String getPuiAutoChk() {
        return puiAutoChk;
    }

    public void setPuiAutoChk(String puiAutoChk) {
        this.puiAutoChk = puiAutoChk;
    }

    public String getDelivWarehReqd() {
        return delivWarehReqd;
    }

    public void setDelivWarehReqd(String delivWarehReqd) {
        this.delivWarehReqd = delivWarehReqd;
    }

    public String getSliAutoGen() {
        return sliAutoGen;
    }

    public void setSliAutoGen(String sliAutoGen) {
        this.sliAutoGen = sliAutoGen;
    }

    public String getSliAutoChk() {
        return sliAutoChk;
    }

    public void setSliAutoChk(String sliAutoChk) {
        this.sliAutoChk = sliAutoChk;
    }

    public String getTranUnitReqd() {
        return tranUnitReqd;
    }

    public void setTranUnitReqd(String tranUnitReqd) {
        this.tranUnitReqd = tranUnitReqd;
    }

    public String getTfReqd() {
        return tfReqd;
    }

    public void setTfReqd(String tfReqd) {
        this.tfReqd = tfReqd;
    }

    public String getRqdCtrl() {
        return rqdCtrl;
    }

    public void setRqdCtrl(String rqdCtrl) {
        this.rqdCtrl = rqdCtrl;
    }

    public String getRqdCtrlAlt() {
        return rqdCtrlAlt;
    }

    public void setRqdCtrlAlt(String rqdCtrlAlt) {
        this.rqdCtrlAlt = rqdCtrlAlt;
    }

    public String getBxiEnabled() {
        return bxiEnabled;
    }

    public void setBxiEnabled(String bxiEnabled) {
        this.bxiEnabled = bxiEnabled;
    }

    public String getBxiEnabledAlt() {
        return bxiEnabledAlt;
    }

    public void setBxiEnabledAlt(String bxiEnabledAlt) {
        this.bxiEnabledAlt = bxiEnabledAlt;
    }

    public String getPscType() {
        return pscType;
    }

    public void setPscType(String pscType) {
        this.pscType = pscType;
    }

    public String getProdCatId() {
        return prodCatId;
    }

    public void setProdCatId(String prodCatId) {
        this.prodCatId = prodCatId;
    }
}