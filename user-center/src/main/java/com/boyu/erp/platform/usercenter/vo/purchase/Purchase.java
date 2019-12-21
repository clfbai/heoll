package com.boyu.erp.platform.usercenter.vo.purchase;


import com.boyu.erp.platform.usercenter.vo.warehouse.StbDtlVo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Classname Purchase
 * @Description TODO
 * @Date 2019/8/15 14:06
 * @Created wz
 */
public class Purchase implements Serializable {

    /**
     * 权限
     */
    private Long sUnitId;
    /**
     * 组织id ×
     */
    private Long unitId;
    /**
     * 单据明细编号
     */
    private String dtlNum;
    /**
     * 单据编号×
     */
    private String docNum;
    /**
     * 单据日期×
     */
    private String docDate;
    /**
     * 启用配码("t","f")
     */
    private String bxiEnabled;

    /**
     * 总数量
     */
    private BigDecimal ttlQty = new BigDecimal("0");

    /**
     * 总箱数
     */
    private Long ttlBox = 0L;

    /**
     * 总金额
     */
    private BigDecimal ttlVal = new BigDecimal("0");

    /**
     * 总税款
     */
    private BigDecimal ttlTax = new BigDecimal("0");

    /**
     * 总市值
     */
    private BigDecimal ttlMkv = new BigDecimal("0");

    /**
     * 桥接方式("d","t")
     */
    private String brdgMode;

    /**
     * 改变成本
     * 注：如果是退购出库或是采购入库，则改变成本为true
     */
    private String costChg;
    /**
     * 中转方ID
     */
    private String tranUnitId;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 库存明细集合
     * 注：WarehDelivTaskServiceImpl中分类对明细进行请求时,封装明细集合时使用
     * @author HHe
     */
    private List<StbDtlVo> stbDtlVos;

    public Long getsUnitId() {
        return sUnitId;
    }

    public void setsUnitId(Long sUnitId) {
        this.sUnitId = sUnitId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getDtlNum() {
        return dtlNum;
    }

    public void setDtlNum(String dtlNum) {
        this.dtlNum = dtlNum;
    }

    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public String getBxiEnabled() {
        return bxiEnabled;
    }

    public void setBxiEnabled(String bxiEnabled) {
        this.bxiEnabled = bxiEnabled;
    }

    public BigDecimal getTtlQty() {
        return ttlQty;
    }

    public void setTtlQty(BigDecimal ttlQty) {
        this.ttlQty = ttlQty;
    }

    public Long getTtlBox() {
        return ttlBox;
    }

    public void setTtlBox(Long ttlBox) {
        this.ttlBox = ttlBox;
    }

    public BigDecimal getTtlVal() {
        return ttlVal;
    }

    public void setTtlVal(BigDecimal ttlVal) {
        this.ttlVal = ttlVal;
    }

    public BigDecimal getTtlTax() {
        return ttlTax;
    }

    public void setTtlTax(BigDecimal ttlTax) {
        this.ttlTax = ttlTax;
    }

    public BigDecimal getTtlMkv() {
        return ttlMkv;
    }

    public void setTtlMkv(BigDecimal ttlMkv) {
        this.ttlMkv = ttlMkv;
    }

    public String getBrdgMode() {
        return brdgMode;
    }

    public void setBrdgMode(String brdgMode) {
        this.brdgMode = brdgMode;
    }

    public String getCostChg() {
        return costChg;
    }

    public void setCostChg(String costChg) {
        this.costChg = costChg;
    }

    public String getTranUnitId() {
        return tranUnitId;
    }

    public void setTranUnitId(String tranUnitId) {
        this.tranUnitId = tranUnitId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<StbDtlVo> getStbDtlVos() {
        return stbDtlVos;
    }

    public void setStbDtlVos(List<StbDtlVo> stbDtlVos) {
        this.stbDtlVos = stbDtlVos;
    }
}


