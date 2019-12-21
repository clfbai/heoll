package com.boyu.erp.platform.usercenter.entity.collarUse;

import java.io.Serializable;

/**
 * arq_type
 * @author 
 */
public class ArqType implements Serializable {
    /**
     * 领用单类别
     */
    private String arqType;

    /**
     * 描述
     */
    private String description;

    /**
     * 须指定发货仓库
     */
    private String delivWarehReqd;

    /**
     * 启用配码
     */
    private String bxiEnabled;

    /**
     * 启用配码可选
     */
    private String bxiEnabledAlt;

    /**
     * 活动
     */
    private String active;

    private static final long serialVersionUID = 1L;

    public String getArqType() {
        return arqType;
    }

    public void setArqType(String arqType) {
        this.arqType = arqType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDelivWarehReqd() {
        return delivWarehReqd;
    }

    public void setDelivWarehReqd(String delivWarehReqd) {
        this.delivWarehReqd = delivWarehReqd;
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

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}