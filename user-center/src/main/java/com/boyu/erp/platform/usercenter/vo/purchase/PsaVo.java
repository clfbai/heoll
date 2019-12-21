package com.boyu.erp.platform.usercenter.vo.purchase;


import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Classname PsaVo
 * @Description TODO
 * @Date 2019/6/21 10:28
 * @Created wz
 */
public class PsaVo implements Serializable {

    /**
     * 判断权限用
     */
    private Long sUnitId;
    /**
     * 供应商ID
     */
    private String venderId;
    /**
     * 供应商编号
     */
    private String venderNum;

    /**
     * 采购商ID
     */
    private String vendeeId;
    /**
     * 采购商编号
     */
    private String vendeeNum;

    /**
     * 协议控制方("r","e")
     */
    private String psaCtlr;
    /**
     * 供应商协议编号
     */
    private String vdrPsaNum;

    /**
     * 采购商协议编号
     */
    private String vdePsaNum;

    /**
     * 生效日期
     */
    private String effDate;

    /**
     * 失效日期
     */
    private String expdDate;

    /**
     * 采购价格参照者ID
     */
    private String puPrlRefId;
    /**
     * 采购价格参照者编号
     */
    private String puPrlRefNum;
    /**
     * 销售价格参照者ID
     */
    private String slPrlRefId;
    /**
     * 销售价格参照者编号
     */
    private String slPrlRefNum;

    /**
     * 缺省折率
     */
    private BigDecimal dfltDiscRate;

    /**
     * 缺省税率
     */
    private BigDecimal dfltTaxRate;

    /**
     * 购销定金比例
     */
    private BigDecimal psDpstRate;

    /**
     * 购销保证金比例
     */
    private BigDecimal psGmRate;

    /**
     * 退货控制("n","q","v")
     */
    private String rtnCtrl;

    /**
     * 缺省代销("t","f")
     */
    private String dfltIsCms;

    /**
     * 缺省可退率
     */
    private BigDecimal dfltRtnaRate;

    /**
     * 退货授信比例
     */
    private BigDecimal rtnAccRate;

    /**
     * 返利兑现比例
     */
    private BigDecimal rwdEncRate;

    /**
     * 中转方ID
     */
    private String tranUnitId;

    /**
     * 购销合同类别
     */
    private String dfltPscType;
    /**
     * 购销合同类别名称
     */
    private String dfltPscTypeDesc;

    /**
     * 退货合同类别
     */
    private String dfltRtcType;
    /**
     * 退货合同类别名称
     */
    private String dfltRtcTypeDesc;

    /**
     * 更新时间
     */
    private String updTime;

    /**
     * 操作员ID
     */
    private String oprId;
    /**
     * 供应商名称
     */
    private String venderName;
    /**
     * 采购商名称
     */
    private String vendeeName;

    /**
     * 采购价格参照者名称
     */
    private String ppriName;

    /**
     * 销售价格参照者名称
     */
    private String spriName;
    /**
     * 中转方代码编号
     */
    private String tranUnitNum;
    /**
     * 中转方名称
     */
    private String tranName;
    /**
     * 零售价格参照者id
     */
    private Long rtPrlRefId;
    /**
     * 零售价格参照者编号
     */
    private String rpriUnitNum;
    /**
     * 零售价格参照者名称
     */
    private String rpriName;
    /**
     * 操作员代码
     */
    private String prsnlCode;
    /**
     * 操作员名称
     */
    private String prsnlName;

    /**
     * 授权方式
     */
    private String authMode;

    /**
     * 经营方式
     */
    private String manMode;

    /**
     * 授权区域
     */
    private String authArea;

    /**
     * 加盟费
     */
    private BigDecimal licFee;

    /**
     * 结账延迟天数
     */
    private Long stlDlyDays;

    /**
     * 是否介入
     */
    private String inte;

    /**
     * 渠道协议对象
     */
    private ChaVo chaVo;

    /**
     * 税率批量添加
     */
    private List<PsaRateVo> trList;

    /**
     * 折率批量添加
     */
    private List<PsaRateVo> drList;


    /**
     * 可退率批量添加
     */
    private List<PsaRateVo> rtrList;

    public PsaVo() {

    }

    public PsaVo(String venderId, String vendeeId) {
        this.venderId = venderId;
        this.vendeeId = vendeeId;
    }

    public PsaVo(String venderId, String vendeeId, String psaCtlr) {
        this.venderId = venderId;
        this.vendeeId = vendeeId;
        this.psaCtlr = psaCtlr;
    }

    public Long getsUnitId() {
        return sUnitId;
    }

    public void setsUnitId(Long sUnitId) {
        this.sUnitId = sUnitId;
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }

    public String getVenderNum() {
        return venderNum;
    }

    public void setVenderNum(String venderNum) {
        this.venderNum = venderNum;
    }

    public String getVendeeId() {
        return vendeeId;
    }

    public void setVendeeId(String vendeeId) {
        this.vendeeId = vendeeId;
    }

    public String getVendeeNum() {
        return vendeeNum;
    }

    public void setVendeeNum(String vendeeNum) {
        this.vendeeNum = vendeeNum;
    }

    public String getPsaCtlr() {
        return psaCtlr;
    }

    public void setPsaCtlr(String psaCtlr) {
        this.psaCtlr = psaCtlr;
    }

    public String getVdrPsaNum() {
        return vdrPsaNum;
    }

    public void setVdrPsaNum(String vdrPsaNum) {
        this.vdrPsaNum = vdrPsaNum;
    }

    public String getVdePsaNum() {
        return vdePsaNum;
    }

    public void setVdePsaNum(String vdePsaNum) {
        this.vdePsaNum = vdePsaNum;
    }

    public String getEffDate() {
        return effDate;
    }

    public void setEffDate(String effDate) {
        this.effDate = effDate;
    }

    public String getExpdDate() {
        return expdDate;
    }

    public void setExpdDate(String expdDate) {
        this.expdDate = expdDate;
    }

    public String getPuPrlRefId() {
        return puPrlRefId;
    }

    public void setPuPrlRefId(String puPrlRefId) {
        this.puPrlRefId = puPrlRefId;
    }

    public String getPuPrlRefNum() {
        return puPrlRefNum;
    }

    public void setPuPrlRefNum(String puPrlRefNum) {
        this.puPrlRefNum = puPrlRefNum;
    }

    public String getSlPrlRefId() {
        return slPrlRefId;
    }

    public void setSlPrlRefId(String slPrlRefId) {
        this.slPrlRefId = slPrlRefId;
    }

    public String getSlPrlRefNum() {
        return slPrlRefNum;
    }

    public void setSlPrlRefNum(String slPrlRefNum) {
        this.slPrlRefNum = slPrlRefNum;
    }

    public BigDecimal getDfltDiscRate() {
        return dfltDiscRate;
    }

    public void setDfltDiscRate(BigDecimal dfltDiscRate) {
        this.dfltDiscRate = dfltDiscRate;
    }

    public BigDecimal getDfltTaxRate() {
        return dfltTaxRate;
    }

    public void setDfltTaxRate(BigDecimal dfltTaxRate) {
        this.dfltTaxRate = dfltTaxRate;
    }

    public BigDecimal getPsDpstRate() {
        return psDpstRate;
    }

    public void setPsDpstRate(BigDecimal psDpstRate) {
        this.psDpstRate = psDpstRate;
    }

    public BigDecimal getPsGmRate() {
        return psGmRate;
    }

    public void setPsGmRate(BigDecimal psGmRate) {
        this.psGmRate = psGmRate;
    }

    public String getRtnCtrl() {
        return rtnCtrl;
    }

    public void setRtnCtrl(String rtnCtrl) {
        this.rtnCtrl = rtnCtrl;
    }

    public String getDfltIsCms() {
        return dfltIsCms;
    }

    public void setDfltIsCms(String dfltIsCms) {
        this.dfltIsCms = dfltIsCms;
    }

    public BigDecimal getDfltRtnaRate() {
        return dfltRtnaRate;
    }

    public void setDfltRtnaRate(BigDecimal dfltRtnaRate) {
        this.dfltRtnaRate = dfltRtnaRate;
    }

    public BigDecimal getRtnAccRate() {
        return rtnAccRate;
    }

    public void setRtnAccRate(BigDecimal rtnAccRate) {
        this.rtnAccRate = rtnAccRate;
    }

    public BigDecimal getRwdEncRate() {
        return rwdEncRate;
    }

    public void setRwdEncRate(BigDecimal rwdEncRate) {
        this.rwdEncRate = rwdEncRate;
    }

    public String getTranUnitId() {
        return tranUnitId;
    }

    public void setTranUnitId(String tranUnitId) {
        this.tranUnitId = tranUnitId;
    }

    public String getDfltPscType() {
        return dfltPscType;
    }

    public void setDfltPscType(String dfltPscType) {
        this.dfltPscType = dfltPscType;
    }

    public String getDfltPscTypeDesc() {
        return dfltPscTypeDesc;
    }

    public void setDfltPscTypeDesc(String dfltPscTypeDesc) {
        this.dfltPscTypeDesc = dfltPscTypeDesc;
    }

    public String getDfltRtcType() {
        return dfltRtcType;
    }

    public void setDfltRtcType(String dfltRtcType) {
        this.dfltRtcType = dfltRtcType;
    }

    public String getDfltRtcTypeDesc() {
        return dfltRtcTypeDesc;
    }

    public void setDfltRtcTypeDesc(String dfltRtcTypeDesc) {
        this.dfltRtcTypeDesc = dfltRtcTypeDesc;
    }

    public String getUpdTime() {
        return updTime;
    }

    public void setUpdTime(String updTime) {
        this.updTime = updTime;
    }

    public String getOprId() {
        return oprId;
    }

    public void setOprId(String oprId) {
        this.oprId = oprId;
    }

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
    }

    public String getVendeeName() {
        return vendeeName;
    }

    public void setVendeeName(String vendeeName) {
        this.vendeeName = vendeeName;
    }

    public String getPpriName() {
        return ppriName;
    }

    public void setPpriName(String ppriName) {
        this.ppriName = ppriName;
    }

    public String getSpriName() {
        return spriName;
    }

    public void setSpriName(String spriName) {
        this.spriName = spriName;
    }

    public String getTranUnitNum() {
        return tranUnitNum;
    }

    public void setTranUnitNum(String tranUnitNum) {
        this.tranUnitNum = tranUnitNum;
    }

    public String getTranName() {
        return tranName;
    }

    public void setTranName(String tranName) {
        this.tranName = tranName;
    }

    public Long getRtPrlRefId() {
        return rtPrlRefId;
    }

    public void setRtPrlRefId(Long rtPrlRefId) {
        this.rtPrlRefId = rtPrlRefId;
    }

    public String getRpriUnitNum() {
        return rpriUnitNum;
    }

    public void setRpriUnitNum(String rpriUnitNum) {
        this.rpriUnitNum = rpriUnitNum;
    }

    public String getRpriName() {
        return rpriName;
    }

    public void setRpriName(String rpriName) {
        this.rpriName = rpriName;
    }

    public String getPrsnlCode() {
        return prsnlCode;
    }

    public void setPrsnlCode(String prsnlCode) {
        this.prsnlCode = prsnlCode;
    }

    public String getPrsnlName() {
        return prsnlName;
    }

    public void setPrsnlName(String prsnlName) {
        this.prsnlName = prsnlName;
    }

    public String getAuthMode() {
        return authMode;
    }

    public void setAuthMode(String authMode) {
        this.authMode = authMode;
    }

    public String getManMode() {
        return manMode;
    }

    public void setManMode(String manMode) {
        this.manMode = manMode;
    }

    public String getAuthArea() {
        return authArea;
    }

    public void setAuthArea(String authArea) {
        this.authArea = authArea;
    }

    public BigDecimal getLicFee() {
        return licFee;
    }

    public void setLicFee(BigDecimal licFee) {
        this.licFee = licFee;
    }

    public Long getStlDlyDays() {
        return stlDlyDays;
    }

    public void setStlDlyDays(Long stlDlyDays) {
        this.stlDlyDays = stlDlyDays;
    }

    public String getInte() {
        return inte;
    }

    public void setInte(String inte) {
        this.inte = inte;
    }

    public ChaVo getChaVo() {
        return chaVo;
    }

    public void setChaVo(ChaVo chaVo) {
        this.chaVo = chaVo;
    }

    public List<PsaRateVo> getTrList() {
        return trList;
    }

    public void setTrList(List<PsaRateVo> trList) {
        this.trList = trList;
    }

    public List<PsaRateVo> getDrList() {
        return drList;
    }

    public void setDrList(List<PsaRateVo> drList) {
        this.drList = drList;
    }

    public List<PsaRateVo> getRtrList() {
        return rtrList;
    }

    public void setRtrList(List<PsaRateVo> rtrList) {
        this.rtrList = rtrList;
    }
}
