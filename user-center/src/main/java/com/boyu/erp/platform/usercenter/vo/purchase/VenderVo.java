package com.boyu.erp.platform.usercenter.vo.purchase;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname VenderVo
 * @Description TODO
 * @Date 2019/6/26 14:38
 * @Created wz
 */
public class VenderVo implements Serializable {

    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 组织筛选id
     */
    private Long sUnitId;
    /**
     * 属主ID
     */
    private Long ownerId;
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
     * 协议控制方
     */
    private String psaCtlr;
    /**
     * 主营商品
     */
    private String majorProd;
    /**
     * 对应采购仓库ID
     */
    private String cpdPuWarehId;
    /**
     * 对应采购仓库编号
     */
    private String cpdPuWarehNum;
    /**
     * 对应采购仓库名称
     */
    private String cpwiUnitName;
    /**
     * 对应退购仓库ID
     */
    private String cpdRpWarehId;
    /**
     * 对应退购仓库编号
     */
    private String cpdRpWarehNum;
    /**
     * 对应退购仓库名称
     */
    private String crwiUnitName;
    /**
     * 受托代销仓库ID
     */
    private String cmsdWarehId;
    /**
     * 受托代销仓库编号
     */
    private String cmsdWarehNum;
    /**
     * 受托代销仓库名称
     */
    private String cwiUnitName;
    /**
     * 供应商状态
     */
    private String vdrStatus;
    /**
     * 国税登记号码
     */
    private String natTaxNum;
    /**
     * 地税登记号码
     */
    private String locTaxNum;
    /**
     * 注册资本
     */
    private BigDecimal regFund;
    /**
     * 开户银行
     */
    private String bank;
    /**
     * 银行账号
     */
    private String bankAcNum;
    /**
     * 供需策略id / 名称
     */
    private String sdPlcyId;
    /**
     * 执照类别
     */
    private String licType;
    /**
     * 执照编号
     */
    private String licNum;
    /**
     * 管理组织ID
     */
    private String ctrlUnitId;
    /**
     * 管理组织编号
     */
    private String ctrlUnitNum;
    /**
     * 管理组织名称
     */
    private String cuiUnitName;
    /**
     * 可征募
     */
    private String recruitable;
    /**
     * 是否共享
     */
    private String shared;
    /**
     * 助记码
     */
    private String inputCode;
    /**
     * 序号
     */
    private String seqNum;
    /**
     * 组织状态
     */
    private String unitStatus;
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
     * 网站
     */
    private String website;
    /**
     * 邮政编码
     */
    private String postcode;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区县
     */
    private String district;
    /**
     * 地址
     */
    private String address;
    /**
     * 联系人id
     */
    private String lmId;
    /**
     * 联系人代码
     */
    private String lmPrsnlCode;
    /**
     * 联系人全名
     */
    private String lmFullName;
    /**
     * 经营状况
     */
    private String manCond;
    /**
     * 信用等级
     */
    private String pcrLvl;
    /**
     * 缺省承运商ID
     */
    private String dfltFwdrId;
    /**
     * 缺省承运商编号
     */
    private String dfltFwdrNum;
    /**
     * 缺省承运商名称
     */
    private String dfltFwdrName;
    /**
     * 缺省送货方式
     */
    private String dfltDelivMthd;
    /**
     * 伙伴状态("a","i","d")
     */
    private String ptnrStatus;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 操作员ID
     */
    private String oprId;
    /**
     * 操作员代码
     */
    private String oprCode;
    /**
     * 操作员名称
     */
    private String oprFullName;
    /**
     * 更新时间
     */
    private String updTime;
    /**
     * 组织类别
     */
    private String unitType;
    /**
     * 组织层级
     */
    private String unitHierarchy;

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getsUnitId() {
        return sUnitId;
    }

    public void setsUnitId(Long sUnitId) {
        this.sUnitId = sUnitId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
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

    public String getPsaCtlr() {
        return psaCtlr;
    }

    public void setPsaCtlr(String psaCtlr) {
        this.psaCtlr = psaCtlr;
    }

    public String getMajorProd() {
        return majorProd;
    }

    public void setMajorProd(String majorProd) {
        this.majorProd = majorProd;
    }

    public String getCpdPuWarehId() {
        return cpdPuWarehId;
    }

    public void setCpdPuWarehId(String cpdPuWarehId) {
        this.cpdPuWarehId = cpdPuWarehId;
    }

    public String getCpdPuWarehNum() {
        return cpdPuWarehNum;
    }

    public void setCpdPuWarehNum(String cpdPuWarehNum) {
        this.cpdPuWarehNum = cpdPuWarehNum;
    }

    public String getCpwiUnitName() {
        return cpwiUnitName;
    }

    public void setCpwiUnitName(String cpwiUnitName) {
        this.cpwiUnitName = cpwiUnitName;
    }

    public String getCpdRpWarehId() {
        return cpdRpWarehId;
    }

    public void setCpdRpWarehId(String cpdRpWarehId) {
        this.cpdRpWarehId = cpdRpWarehId;
    }

    public String getCpdRpWarehNum() {
        return cpdRpWarehNum;
    }

    public void setCpdRpWarehNum(String cpdRpWarehNum) {
        this.cpdRpWarehNum = cpdRpWarehNum;
    }

    public String getCrwiUnitName() {
        return crwiUnitName;
    }

    public void setCrwiUnitName(String crwiUnitName) {
        this.crwiUnitName = crwiUnitName;
    }

    public String getCmsdWarehId() {
        return cmsdWarehId;
    }

    public void setCmsdWarehId(String cmsdWarehId) {
        this.cmsdWarehId = cmsdWarehId;
    }

    public String getCmsdWarehNum() {
        return cmsdWarehNum;
    }

    public void setCmsdWarehNum(String cmsdWarehNum) {
        this.cmsdWarehNum = cmsdWarehNum;
    }

    public String getCwiUnitName() {
        return cwiUnitName;
    }

    public void setCwiUnitName(String cwiUnitName) {
        this.cwiUnitName = cwiUnitName;
    }

    public String getVdrStatus() {
        return vdrStatus;
    }

    public void setVdrStatus(String vdrStatus) {
        this.vdrStatus = vdrStatus;
    }

    public String getNatTaxNum() {
        return natTaxNum;
    }

    public void setNatTaxNum(String natTaxNum) {
        this.natTaxNum = natTaxNum;
    }

    public String getLocTaxNum() {
        return locTaxNum;
    }

    public void setLocTaxNum(String locTaxNum) {
        this.locTaxNum = locTaxNum;
    }

    public BigDecimal getRegFund() {
        return regFund;
    }

    public void setRegFund(BigDecimal regFund) {
        this.regFund = regFund;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankAcNum() {
        return bankAcNum;
    }

    public void setBankAcNum(String bankAcNum) {
        this.bankAcNum = bankAcNum;
    }

    public String getSdPlcyId() {
        return sdPlcyId;
    }

    public void setSdPlcyId(String sdPlcyId) {
        this.sdPlcyId = sdPlcyId;
    }

    public String getLicType() {
        return licType;
    }

    public void setLicType(String licType) {
        this.licType = licType;
    }

    public String getLicNum() {
        return licNum;
    }

    public void setLicNum(String licNum) {
        this.licNum = licNum;
    }

    public String getCtrlUnitId() {
        return ctrlUnitId;
    }

    public void setCtrlUnitId(String ctrlUnitId) {
        this.ctrlUnitId = ctrlUnitId;
    }

    public String getCtrlUnitNum() {
        return ctrlUnitNum;
    }

    public void setCtrlUnitNum(String ctrlUnitNum) {
        this.ctrlUnitNum = ctrlUnitNum;
    }

    public String getCuiUnitName() {
        return cuiUnitName;
    }

    public void setCuiUnitName(String cuiUnitName) {
        this.cuiUnitName = cuiUnitName;
    }

    public String getRecruitable() {
        return recruitable;
    }

    public void setRecruitable(String recruitable) {
        this.recruitable = recruitable;
    }

    public String getShared() {
        return shared;
    }

    public void setShared(String shared) {
        this.shared = shared;
    }

    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    public String getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(String seqNum) {
        this.seqNum = seqNum;
    }

    public String getUnitStatus() {
        return unitStatus;
    }

    public void setUnitStatus(String unitStatus) {
        this.unitStatus = unitStatus;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLmId() {
        return lmId;
    }

    public void setLmId(String lmId) {
        this.lmId = lmId;
    }

    public String getLmPrsnlCode() {
        return lmPrsnlCode;
    }

    public void setLmPrsnlCode(String lmPrsnlCode) {
        this.lmPrsnlCode = lmPrsnlCode;
    }

    public String getLmFullName() {
        return lmFullName;
    }

    public void setLmFullName(String lmFullName) {
        this.lmFullName = lmFullName;
    }

    public String getManCond() {
        return manCond;
    }

    public void setManCond(String manCond) {
        this.manCond = manCond;
    }

    public String getPcrLvl() {
        return pcrLvl;
    }

    public void setPcrLvl(String pcrLvl) {
        this.pcrLvl = pcrLvl;
    }

    public String getDfltFwdrId() {
        return dfltFwdrId;
    }

    public void setDfltFwdrId(String dfltFwdrId) {
        this.dfltFwdrId = dfltFwdrId;
    }

    public String getDfltFwdrNum() {
        return dfltFwdrNum;
    }

    public void setDfltFwdrNum(String dfltFwdrNum) {
        this.dfltFwdrNum = dfltFwdrNum;
    }

    public String getDfltFwdrName() {
        return dfltFwdrName;
    }

    public void setDfltFwdrName(String dfltFwdrName) {
        this.dfltFwdrName = dfltFwdrName;
    }

    public String getDfltDelivMthd() {
        return dfltDelivMthd;
    }

    public void setDfltDelivMthd(String dfltDelivMthd) {
        this.dfltDelivMthd = dfltDelivMthd;
    }

    public String getPtnrStatus() {
        return ptnrStatus;
    }

    public void setPtnrStatus(String ptnrStatus) {
        this.ptnrStatus = ptnrStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOprId() {
        return oprId;
    }

    public void setOprId(String oprId) {
        this.oprId = oprId;
    }

    public String getOprCode() {
        return oprCode;
    }

    public void setOprCode(String oprCode) {
        this.oprCode = oprCode;
    }

    public String getOprFullName() {
        return oprFullName;
    }

    public void setOprFullName(String oprFullName) {
        this.oprFullName = oprFullName;
    }

    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updTime) {
        this.updTime = updTime;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getUnitHierarchy() {
        return unitHierarchy;
    }

    public void setUnitHierarchy(String unitHierarchy) {
        this.unitHierarchy = unitHierarchy;
    }
}
