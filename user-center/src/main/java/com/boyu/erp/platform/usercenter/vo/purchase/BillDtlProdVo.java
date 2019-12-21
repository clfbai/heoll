package com.boyu.erp.platform.usercenter.vo.purchase;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Classname BillDtlProdVo
 * @Description TODO
 * @Date 2019/7/10 14:24
 * @Created wz
 */
public class BillDtlProdVo implements Serializable {

    /**
     * 当前组织/切换组织
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
     * 当前用户id
     */
    private Long userId;
    /**
     * 商品ID
     */
    private Long prodId;
    /**
     * 商品品种ID  主键
     */
    private Long prodClsId;
    /**
     * 商品品种代码  非空
     */
    private String prodClsCode;
    /**
     * 商品名称
     */
    private String prodName;

    /**
     * 零售单价
     */
    private Float rtUnitPrice;
    /**
     * 助记码
     */
    private String inputCode;
    /**
     * 版型   非空
     */
    private String edition;
    /**
     * 颜色ID   非空
     */
    private String color;
    /**
     * 规格ID   非空
     */
    private String spec;
    /**
     * 商品代码
     */
    private String prodCode;
    /**
     * 分销单价
     */
    private Float wsUnitPrice;
    /**
     * 挂牌价
     */
    private BigDecimal lstPrice;
    /**
     * 计量单位
     */
    private String uom;
    /**
     * 序号
     */
    private Integer seqNum;
    /**
     * 商品分类Id
     */
    private String prodCatId;
    /**
     * 品牌id
     */
    private String brandId;
    /**
     * 商品状态
     */
    private String prodStatus;

    /**
     * 折率
     */
    private BigDecimal discRate;
    /**
     * 采购税率
     */
    private BigDecimal taxRate;
    /**
     * 分销税率
     */
    private BigDecimal wsTaxRate;
    /**
     * 可退率
     */
    private BigDecimal rtnaRate;
    /**
     * 采购单价
     */
    private BigDecimal unitPrice;
    /**
     * 折后价
     */
    private BigDecimal fnlPrice;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public Long getProdClsId() {
        return prodClsId;
    }

    public void setProdClsId(Long prodClsId) {
        this.prodClsId = prodClsId;
    }

    public String getProdClsCode() {
        return prodClsCode;
    }

    public void setProdClsCode(String prodClsCode) {
        this.prodClsCode = prodClsCode;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Float getRtUnitPrice() {
        return rtUnitPrice;
    }

    public void setRtUnitPrice(Float rtUnitPrice) {
        this.rtUnitPrice = rtUnitPrice;
    }

    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public Float getWsUnitPrice() {
        return wsUnitPrice;
    }

    public void setWsUnitPrice(Float wsUnitPrice) {
        this.wsUnitPrice = wsUnitPrice;
    }

    public BigDecimal getLstPrice() {
        return lstPrice;
    }

    public void setLstPrice(BigDecimal lstPrice) {
        this.lstPrice = lstPrice;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public Integer getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Integer seqNum) {
        this.seqNum = seqNum;
    }

    public String getProdCatId() {
        return prodCatId;
    }

    public void setProdCatId(String prodCatId) {
        this.prodCatId = prodCatId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getProdStatus() {
        return prodStatus;
    }

    public void setProdStatus(String prodStatus) {
        this.prodStatus = prodStatus;
    }

    public BigDecimal getDiscRate() {
        return discRate;
    }

    public void setDiscRate(BigDecimal discRate) {
        this.discRate = discRate;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getWsTaxRate() {
        return wsTaxRate;
    }

    public void setWsTaxRate(BigDecimal wsTaxRate) {
        this.wsTaxRate = wsTaxRate;
    }

    public BigDecimal getRtnaRate() {
        return rtnaRate;
    }

    public void setRtnaRate(BigDecimal rtnaRate) {
        this.rtnaRate = rtnaRate;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getFnlPrice() {
        return fnlPrice;
    }

    public void setFnlPrice(BigDecimal fnlPrice) {
        this.fnlPrice = fnlPrice;
    }
}
