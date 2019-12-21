package com.boyu.erp.platform.usercenter.vo.purchase;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @Classname VdrSupplyVo
 * @Description TODO
 * @Date 2019/8/5 10:53
 * @Created wz
 */
public class VdrSupplyVo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 判断权限
     */
    private Long sUnitId;
    /**
     * 供应商
     */
    private Long venderId;
    /**
     * 采购商
     */
    private Long vendeeId;
    /**
     * 组织代码
     */
    private String unitCode;
    /**
     * 组织编号
     */
    private String unitNum;
    /**
     * 组织名称
     */
    private String unitName;
    /**
     * 供应商组织状态
     */
    private String vdrStatus;
    /**
     * 采购商组织状态
     */
    private String vdeStatus;
    /**
     * 助记码
     */
    private String inputCode;
    /**
     * 电话号码
     */
    private String telNum;
    /**
     * 传真号码
     */
    private String faxNum;
    /**
     * 电子邮件地址
     */
    private String emailAddr;

    /**
     * 操作员ID
     */
    private String oprId;
    /**
     * 操作员名称
     */
    private String oprFullName;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getVdeStatus() {
        return vdeStatus;
    }

    public void setVdeStatus(String vdeStatus) {
        this.vdeStatus = vdeStatus;
    }

    public Long getsUnitId() {
        return sUnitId;
    }

    public void setsUnitId(Long sUnitId) {
        this.sUnitId = sUnitId;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public Long getVendeeId() {
        return vendeeId;
    }

    public void setVendeeId(Long vendeeId) {
        this.vendeeId = vendeeId;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(String unitNum) {
        this.unitNum = unitNum;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getVdrStatus() {
        return vdrStatus;
    }

    public void setVdrStatus(String vdrStatus) {
        this.vdrStatus = vdrStatus;
    }

    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public String getFaxNum() {
        return faxNum;
    }

    public void setFaxNum(String faxNum) {
        this.faxNum = faxNum;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public String getOprId() {
        return oprId;
    }

    public void setOprId(String oprId) {
        this.oprId = oprId;
    }

    public String getOprFullName() {
        return oprFullName;
    }

    public void setOprFullName(String oprFullName) {
        this.oprFullName = oprFullName;
    }
}
