package com.boyu.erp.platform.usercenter.vo.purchase;


import com.boyu.erp.platform.usercenter.utils.StringUtils;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Classname PrcVo
 * @Description TODO
 * @Date 2019/7/25 15:49
 * @Created wz
 */
public class PrcVo extends Purchase implements Serializable {

    /**
     * 退购合同已生成("t","f")
     */
    private String prcGen;
    /**
     * 采购商介入
     */
    private String vdeInvd;
    /**
     * 退购合同号
     */
    private String prcNum;
    /**
     * 退销合同号
     */
    private String srcNum;
    /**
     * 退购合同类别
     */
    private String prcType;
    /**
     * 退销合同类别
     */
    private String srcType;
    /**
     * 退货合同类别
     */
    private String rtcType;
    /**
     * 供应商ID
     */
    private Long venderId;
    /**
     * 供应商编号
     */
    private String venderNum;
    /**
     * 供应商名称
     */
    private String venderName;
    /**
     * 采购商ID
     */
    private Long vendeeId;
    /**
     * 采购商编号
     */
    private String vendeeNum;
    /**
     * 采购商名称
     */
    private String vendeeName;
    /**
     * 控制协议方
     */
    private String psaCtrl;
    /**
     * 采购商仓库ID
     */
    private Long vdeWarehId;
    /**
     * 采购商仓库编号
     */
    private String vdeWarehNum;
    /**
     * 采购商仓库名称
     */
    private String vdeWarehName;
    /**
     * 供应商仓库ID
     */
    private Long vdrWarehId;
    /**
     * 供应商仓库编号
     */
    private String vdrWarehNum;
    /**
     * 供应商仓库名称
     */
    private String vdrWarehName;
    /**
     * 始发方ID
     */
    private Long stUnitId;
    /**
     * 始发方编号
     */
    private String stUnitNum;
    /**
     * 始发方名称
     */
    private String stUnitName;
    /**
     * 始发仓库ID
     */
    private Long stWarehId;
    /**
     * 始发仓库编号
     */
    private String stWarehNum;
    /**
     * 始发仓库名称
     */
    private String stWarehName;
    /**
     * 转送方ID
     */
    private Long endUnitId;
    /**
     * 转送方编号
     */
    private String endUnitNum;
    /**
     * 转送方名称
     */
    private String endUnitName;
    /**
     * 转送仓库ID
     */
    private Long endWarehId;
    /**
     * 转送仓库编号
     */
    private String endWarehNum;
    /**
     * 转送仓库名称
     */
    private String endWarehName;
    /**
     * 中转方编号
     */
    private String tranUnitNum;
    /**
     * 中转方名称
     */
    private String tranUnitName;
    /**
     * 采购商会计组织ID
     */
    private Long vdeFsclUnitId;
    /**
     * 采购商会计组织代码
     */
    private String vdeFsclUnitCode;
    /**
     * 采购商会计组织名称
     */
    private String vdeFsclUnitName;
    /**
     * 供应商会计组织ID
     */
    private Long vdrFsclUnitId;
    /**
     * 供应商会计组织代码
     */
    private String vdrFsclUnitCode;
    /**
     * 供应商会计组织名称
     */
    private String vdrFsclUnitName;
    /**
     * 供应商介入("t","f")
     */
    private String vdrInvd;
    /**
     * 退销合同自动生成("t","f")
     */
    private String srcAutoGen;
    /**
     * 退销合同自动审核("t","f")
     */
    private String srcAutoChk;
    /**
     * 退销合同已生成("t","f")
     */
    private String srcGen;
    /**
     * 退购合同自动生成("t","f")
     */
    private String prcAutoGen;
    /**
     * 退购合同自动审核("t","f")
     */
    private String prcAutoChk;
    /**
     * 定价点("ct","dl","rc")
     */
    private String prcSite;
    /**
     * 货期
     */
    private String reqdDate;
    /**
     * 是否代销("t","f")
     */
    private String isCms;
    /**
     * 关联购销合同("t","f")
     */
    private String pscReqd;

    /**
     * 购销合同号
     */
    private String pscNum;
    /**
     * 退购桥接方式("d","t")
     */
    private String prBrdgMode;
    /**
     * 退销桥接方式("d","t")
     */
    private String srBrdgMode;
    /**
     * 是否居间退购("t","f")
     */
    private String isPrItmd;
    /**
     * 是否居间退销("t","f")
     */
    private String isSrItmd;
    /**
     * 多次执行("t","f")
     */
    private String multiImpl;
    /**
     * 差异裁定方("d","r")
     */
    private String drDiffJgd;
    /**
     * 占用可退额度("t","f")
     */
    private String rtnaInvd;
    /**
     * 启用授信("t","f")
     */
    private String accEnabled;
    /**
     * 授信点("ck","dd")
     */
    private String rtnAccSite;
    /**
     * 允许增补商品("t","f")
     */
    private String splEnabled;
    /**
     * 超额许可比例
     */
    private BigDecimal exblRate;
    /**
     * 授信金额
     */
    private BigDecimal accVal;
    /**
     * 原始单据类别
     */
    private String srcDocType;
    /**
     * 原始单据组织id
     */
    private Long srcDocUnitId;
    /**
     * 原始单据编号
     */
    private String srcDocNum;
    /**
     * 商品分类ID
     */
    private String prodCatId;
    /**
     * 商品分类名称
     */
    private String prodCatName;
    /**
     * 退单类别
     */
    private String rtnType;
    /**
     * 失效日期
     */
    private String expdDate;
    /**
     * 送货方式
     */
    private String delivMthd;
    /**
     * 邮政编码
     */
    private String delivPstd;
    /**
     * 送货地址
     */
    private String delivAddr;
    /**
     * 发货总数量
     */
    private BigDecimal ttlDelivQty;
    /**
     * 发货总箱数
     */
    private Long ttlDelivBox;
    /**
     * 发货总金额
     */
    private BigDecimal ttlDelivVal;
    /**
     * 发货总税款
     */
    private BigDecimal ttlDelivTax;
    /**
     * 发货总市值
     */
    private BigDecimal ttlDelivMkv;
    /**
     * 到货总数量
     */
    private BigDecimal ttlRcvQty;
    /**
     * 到货总箱数
     */
    private Long ttlRcvBox;
    /**
     * 到货总金额
     */
    private BigDecimal ttlRcvVal;
    /**
     * 到货总税款
     */
    private BigDecimal ttlRcvTax;
    /**
     * 到货总市值
     */
    private BigDecimal ttlRcvMkv;
    /**
     * 操作员ID
     */
    private Long oprId;
    /**
     * 操作员代码
     */
    private String oprCode;
    /**
     * 操作员名称
     */
    private String oprName;
    /**
     * 操作时间
     */
    private String opTime;
    /**
     * 审核人id
     */
    private Long chkrId;
    /**
     * 审核人编号
     */
    private String chkrNum;
    /**
     * 审核人名称
     */
    private String chkrName;
    /**
     * 审核时间
     */
    private String chkTime;
    /**
     * 已生效("t","f")
     */
    private String effective;
    /**
     * 进度("pg","cn","rk","ek","ck","dg","dd","rg","rd","cl")
     */
    private String progress;
    /**
     * 撤销("t","f")
     */
    private String cancelled;
    /**
     * 挂起("t","f")
     */
    private String suspended;
    /**
     * 执行合同编号
     */
    private String execRtcNum = "";
    /**
     * 居间合同编号
     */
    private String itmdRtcNum = "";
    /**
     * 始发合同编号
     */
    private String stRtcNum;
    /**
     * 转送合同编号
     */
    private String endRtcNum;
    /**
     * 绑定附加成本("t","f")
     */
    private String acReqd;
    /**
     * 附加成本已绑定("t","f")
     */
    private String acBnd;

    /**
     * 附加成本绑定人id
     */
    private Long acBndrId;
    /**
     * 附加成本绑定人编号
     */
    private String acBndrNum;
    /**
     * 附加成本绑定人名称
     */
    private String acBndrName;
    /**
     * 附加成本绑定时间
     */
    private String acBndTime;
    /**
     * 绑定附加成本("t","f")
     */
    private String afReqd;
    /**
     * 附加成本已绑定("t","f")
     */
    private String afBnd;

    /**
     * 附加成本绑定人id
     */
    private Long afBndrId;
    /**
     * 附加成本绑定人编号
     */
    private String afBndrNum;
    /**
     * 附加成本绑定人名称
     */
    private String afBndrName;
    /**
     * 附加成本绑定时间
     */
    private String afBndTime;
    /**
     * 已转退销("t","f")
     */
    private String srFwdd;
    /**
     * 已转退销("t","f")
     */
    private String prFwdd;
    /**
     * 退货合同号
     */
    private String rtcNum;
    /**
     * 搜索条件 开始日期
     */
    private String startTime;
    /**
     * 搜索条件结束日期
     */
    private String endTime;
    /**
     * 控制范围sql 拼接map
     */
    private Map<String, Object> map;

    /**
     * 发货中任务数
     */
    private Long tasksInDeliv = 0L;

    /**
     * 收货中任务数
     */
    private Long tasksInRcv = 0L;

    /**
     * 批量添加明细
     */
    private List<RtcDtlVo> rtcDtlList;

    public PrcVo() {
    }

    public PrcVo(String rtcNum, List<RtcDtlVo> rtcDtlList) {
        this.rtcNum = rtcNum;
        this.rtcDtlList = rtcDtlList;
    }

    public PrcVo(String rtcNum) {
        this.rtcNum = rtcNum;
    }

    public PrcVo(String prcNum, String srcNum) {
        this.prcNum = prcNum;
        this.srcNum = srcNum;
    }
    public PrcVo(String prcNum, String srcNum, String unitId) {
        this.prcNum = prcNum;
        this.srcNum = srcNum;
        super.setUnitId(Long.valueOf(unitId));
    }

    public String getPrcGen() {
        return prcGen;
    }

    public void setPrcGen(String prcGen) {
        this.prcGen = prcGen;
    }

    public String getVdeInvd() {
        return vdeInvd;
    }

    public void setVdeInvd(String vdeInvd) {
        this.vdeInvd = vdeInvd;
    }

    public String getPrcNum() {
        return prcNum;
    }

    public void setPrcNum(String prcNum) {
        this.prcNum = prcNum;
        if(StringUtils.isNotEmpty(prcNum)){
            super.setDocNum(prcNum);
        }
    }

    public String getSrcNum() {
        return srcNum;
    }

    public void setSrcNum(String srcNum) {
        this.srcNum = srcNum;
        if(StringUtils.isNotEmpty(srcNum)){
            super.setDocNum(srcNum);
        }
    }

    public String getPrcType() {
        return prcType;
    }

    public void setPrcType(String prcType) {
        this.prcType = prcType;
    }

    public String getSrcType() {
        return srcType;
    }

    public void setSrcType(String srcType) {
        this.srcType = srcType;
    }

    public String getRtcType() {
        return rtcType;
    }

    public void setRtcType(String rtcType) {
        this.rtcType = rtcType;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public String getVenderNum() {
        return venderNum;
    }

    public void setVenderNum(String venderNum) {
        this.venderNum = venderNum;
    }

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
    }

    public String getPsaCtrl() {
        return psaCtrl;
    }

    public void setPsaCtrl(String psaCtrl) {
        this.psaCtrl = psaCtrl;
    }

    public Long getVdeWarehId() {
        return vdeWarehId;
    }

    public void setVdeWarehId(Long vdeWarehId) {
        this.vdeWarehId = vdeWarehId;
    }

    public String getVdeWarehNum() {
        return vdeWarehNum;
    }

    public void setVdeWarehNum(String vdeWarehNum) {
        this.vdeWarehNum = vdeWarehNum;
    }

    public String getVdeWarehName() {
        return vdeWarehName;
    }

    public void setVdeWarehName(String vdeWarehName) {
        this.vdeWarehName = vdeWarehName;
    }

    public Long getVdrWarehId() {
        return vdrWarehId;
    }

    public void setVdrWarehId(Long vdrWarehId) {
        this.vdrWarehId = vdrWarehId;
    }

    public String getVdrWarehNum() {
        return vdrWarehNum;
    }

    public void setVdrWarehNum(String vdrWarehNum) {
        this.vdrWarehNum = vdrWarehNum;
    }

    public String getVdrWarehName() {
        return vdrWarehName;
    }

    public void setVdrWarehName(String vdrWarehName) {
        this.vdrWarehName = vdrWarehName;
    }

    public Long getStUnitId() {
        return stUnitId;
    }

    public void setStUnitId(Long stUnitId) {
        this.stUnitId = stUnitId;
    }

    public String getStUnitNum() {
        return stUnitNum;
    }

    public void setStUnitNum(String stUnitNum) {
        this.stUnitNum = stUnitNum;
    }

    public String getStUnitName() {
        return stUnitName;
    }

    public void setStUnitName(String stUnitName) {
        this.stUnitName = stUnitName;
    }

    public Long getStWarehId() {
        return stWarehId;
    }

    public void setStWarehId(Long stWarehId) {
        this.stWarehId = stWarehId;
    }

    public String getStWarehNum() {
        return stWarehNum;
    }

    public void setStWarehNum(String stWarehNum) {
        this.stWarehNum = stWarehNum;
    }

    public String getStWarehName() {
        return stWarehName;
    }

    public void setStWarehName(String stWarehName) {
        this.stWarehName = stWarehName;
    }

    public Long getEndUnitId() {
        return endUnitId;
    }

    public void setEndUnitId(Long endUnitId) {
        this.endUnitId = endUnitId;
    }

    public String getEndUnitNum() {
        return endUnitNum;
    }

    public void setEndUnitNum(String endUnitNum) {
        this.endUnitNum = endUnitNum;
    }

    public String getEndUnitName() {
        return endUnitName;
    }

    public void setEndUnitName(String endUnitName) {
        this.endUnitName = endUnitName;
    }

    public Long getEndWarehId() {
        return endWarehId;
    }

    public void setEndWarehId(Long endWarehId) {
        this.endWarehId = endWarehId;
    }

    public String getEndWarehNum() {
        return endWarehNum;
    }

    public void setEndWarehNum(String endWarehNum) {
        this.endWarehNum = endWarehNum;
    }

    public String getEndWarehName() {
        return endWarehName;
    }

    public void setEndWarehName(String endWarehName) {
        this.endWarehName = endWarehName;
    }

    public String getTranUnitNum() {
        return tranUnitNum;
    }

    public void setTranUnitNum(String tranUnitNum) {
        this.tranUnitNum = tranUnitNum;
    }

    public String getTranUnitName() {
        return tranUnitName;
    }

    public void setTranUnitName(String tranUnitName) {
        this.tranUnitName = tranUnitName;
    }

    public Long getVdeFsclUnitId() {
        return vdeFsclUnitId;
    }

    public void setVdeFsclUnitId(Long vdeFsclUnitId) {
        this.vdeFsclUnitId = vdeFsclUnitId;
    }

    public String getVdeFsclUnitCode() {
        return vdeFsclUnitCode;
    }

    public void setVdeFsclUnitCode(String vdeFsclUnitCode) {
        this.vdeFsclUnitCode = vdeFsclUnitCode;
    }

    public String getVdeFsclUnitName() {
        return vdeFsclUnitName;
    }

    public void setVdeFsclUnitName(String vdeFsclUnitName) {
        this.vdeFsclUnitName = vdeFsclUnitName;
    }

    public Long getVdrFsclUnitId() {
        return vdrFsclUnitId;
    }

    public void setVdrFsclUnitId(Long vdrFsclUnitId) {
        this.vdrFsclUnitId = vdrFsclUnitId;
    }

    public String getVdrFsclUnitCode() {
        return vdrFsclUnitCode;
    }

    public void setVdrFsclUnitCode(String vdrFsclUnitCode) {
        this.vdrFsclUnitCode = vdrFsclUnitCode;
    }

    public String getVdrFsclUnitName() {
        return vdrFsclUnitName;
    }

    public void setVdrFsclUnitName(String vdrFsclUnitName) {
        this.vdrFsclUnitName = vdrFsclUnitName;
    }

    public String getVdrInvd() {
        return vdrInvd;
    }

    public void setVdrInvd(String vdrInvd) {
        this.vdrInvd = vdrInvd;
    }

    public String getSrcAutoGen() {
        return srcAutoGen;
    }

    public void setSrcAutoGen(String srcAutoGen) {
        this.srcAutoGen = srcAutoGen;
    }

    public String getSrcAutoChk() {
        return srcAutoChk;
    }

    public void setSrcAutoChk(String srcAutoChk) {
        this.srcAutoChk = srcAutoChk;
    }

    public String getSrcGen() {
        return srcGen;
    }

    public void setSrcGen(String srcGen) {
        this.srcGen = srcGen;
    }

    public String getPrcAutoGen() {
        return prcAutoGen;
    }

    public void setPrcAutoGen(String prcAutoGen) {
        this.prcAutoGen = prcAutoGen;
    }

    public String getPrcAutoChk() {
        return prcAutoChk;
    }

    public void setPrcAutoChk(String prcAutoChk) {
        this.prcAutoChk = prcAutoChk;
    }

    public String getPrcSite() {
        return prcSite;
    }

    public void setPrcSite(String prcSite) {
        this.prcSite = prcSite;
    }

    public String getReqdDate() {
        return reqdDate;
    }

    public void setReqdDate(String reqdDate) {
        this.reqdDate = reqdDate;
    }

    public String getIsCms() {
        return isCms;
    }

    public void setIsCms(String isCms) {
        this.isCms = isCms;
    }

    public String getPscReqd() {
        return pscReqd;
    }

    public void setPscReqd(String pscReqd) {
        this.pscReqd = pscReqd;
    }

    public String getPscNum() {
        return pscNum;
    }

    public void setPscNum(String pscNum) {
        this.pscNum = pscNum;
    }

    public String getPrBrdgMode() {
        return prBrdgMode;
    }

    public void setPrBrdgMode(String prBrdgMode) {
        this.prBrdgMode = prBrdgMode;
        if(StringUtils.isNotEmpty(prBrdgMode)){
            super.setBrdgMode(prBrdgMode);
        }
    }

    public String getSrBrdgMode() {
        return srBrdgMode;
    }

    public void setSrBrdgMode(String srBrdgMode) {
        this.srBrdgMode = srBrdgMode;
        if(StringUtils.isNotEmpty(srBrdgMode)){
            super.setBrdgMode(srBrdgMode);
        }
    }

    public String getIsPrItmd() {
        return isPrItmd;
    }

    public void setIsPrItmd(String isPrItmd) {
        this.isPrItmd = isPrItmd;
    }

    public String getIsSrItmd() {
        return isSrItmd;
    }

    public void setIsSrItmd(String isSrItmd) {
        this.isSrItmd = isSrItmd;
    }

    public String getMultiImpl() {
        return multiImpl;
    }

    public void setMultiImpl(String multiImpl) {
        this.multiImpl = multiImpl;
    }

    public String getDrDiffJgd() {
        return drDiffJgd;
    }

    public void setDrDiffJgd(String drDiffJgd) {
        this.drDiffJgd = drDiffJgd;
    }

    public String getRtnaInvd() {
        return rtnaInvd;
    }

    public void setRtnaInvd(String rtnaInvd) {
        this.rtnaInvd = rtnaInvd;
    }

    public String getAccEnabled() {
        return accEnabled;
    }

    public void setAccEnabled(String accEnabled) {
        this.accEnabled = accEnabled;
    }

    public String getRtnAccSite() {
        return rtnAccSite;
    }

    public void setRtnAccSite(String rtnAccSite) {
        this.rtnAccSite = rtnAccSite;
    }

    public String getSplEnabled() {
        return splEnabled;
    }

    public void setSplEnabled(String splEnabled) {
        this.splEnabled = splEnabled;
    }

    public BigDecimal getExblRate() {
        return exblRate;
    }

    public void setExblRate(BigDecimal exblRate) {
        this.exblRate = exblRate;
    }

    public BigDecimal getAccVal() {
        return accVal;
    }

    public void setAccVal(BigDecimal accVal) {
        this.accVal = accVal;
    }

    public String getSrcDocType() {
        return srcDocType;
    }

    public void setSrcDocType(String srcDocType) {
        this.srcDocType = srcDocType;
    }

    public Long getSrcDocUnitId() {
        return srcDocUnitId;
    }

    public void setSrcDocUnitId(Long srcDocUnitId) {
        this.srcDocUnitId = srcDocUnitId;
    }

    public String getSrcDocNum() {
        return srcDocNum;
    }

    public void setSrcDocNum(String srcDocNum) {
        this.srcDocNum = srcDocNum;
    }

    public String getProdCatId() {
        return prodCatId;
    }

    public void setProdCatId(String prodCatId) {
        this.prodCatId = prodCatId;
    }

    public String getProdCatName() {
        return prodCatName;
    }

    public void setProdCatName(String prodCatName) {
        this.prodCatName = prodCatName;
    }

    public String getRtnType() {
        return rtnType;
    }

    public void setRtnType(String rtnType) {
        this.rtnType = rtnType;
    }

    public String getExpdDate() {
        return expdDate;
    }

    public void setExpdDate(String expdDate) {
        this.expdDate = expdDate;
    }

    public String getDelivMthd() {
        return delivMthd;
    }

    public void setDelivMthd(String delivMthd) {
        this.delivMthd = delivMthd;
    }

    public String getDelivPstd() {
        return delivPstd;
    }

    public void setDelivPstd(String delivPstd) {
        this.delivPstd = delivPstd;
    }

    public String getDelivAddr() {
        return delivAddr;
    }

    public void setDelivAddr(String delivAddr) {
        this.delivAddr = delivAddr;
    }

    public BigDecimal getTtlDelivQty() {
        return ttlDelivQty;
    }

    public void setTtlDelivQty(BigDecimal ttlDelivQty) {
        this.ttlDelivQty = ttlDelivQty;
    }

    public Long getTtlDelivBox() {
        return ttlDelivBox;
    }

    public void setTtlDelivBox(Long ttlDelivBox) {
        this.ttlDelivBox = ttlDelivBox;
    }

    public BigDecimal getTtlDelivVal() {
        return ttlDelivVal;
    }

    public void setTtlDelivVal(BigDecimal ttlDelivVal) {
        this.ttlDelivVal = ttlDelivVal;
    }

    public BigDecimal getTtlDelivTax() {
        return ttlDelivTax;
    }

    public void setTtlDelivTax(BigDecimal ttlDelivTax) {
        this.ttlDelivTax = ttlDelivTax;
    }

    public BigDecimal getTtlDelivMkv() {
        return ttlDelivMkv;
    }

    public void setTtlDelivMkv(BigDecimal ttlDelivMkv) {
        this.ttlDelivMkv = ttlDelivMkv;
    }

    public BigDecimal getTtlRcvQty() {
        return ttlRcvQty;
    }

    public void setTtlRcvQty(BigDecimal ttlRcvQty) {
        this.ttlRcvQty = ttlRcvQty;
    }

    public Long getTtlRcvBox() {
        return ttlRcvBox;
    }

    public void setTtlRcvBox(Long ttlRcvBox) {
        this.ttlRcvBox = ttlRcvBox;
    }

    public BigDecimal getTtlRcvVal() {
        return ttlRcvVal;
    }

    public void setTtlRcvVal(BigDecimal ttlRcvVal) {
        this.ttlRcvVal = ttlRcvVal;
    }

    public BigDecimal getTtlRcvTax() {
        return ttlRcvTax;
    }

    public void setTtlRcvTax(BigDecimal ttlRcvTax) {
        this.ttlRcvTax = ttlRcvTax;
    }

    public BigDecimal getTtlRcvMkv() {
        return ttlRcvMkv;
    }

    public void setTtlRcvMkv(BigDecimal ttlRcvMkv) {
        this.ttlRcvMkv = ttlRcvMkv;
    }

    public Long getOprId() {
        return oprId;
    }

    public void setOprId(Long oprId) {
        this.oprId = oprId;
    }

    public String getOprCode() {
        return oprCode;
    }

    public void setOprCode(String oprCode) {
        this.oprCode = oprCode;
    }

    public String getOprName() {
        return oprName;
    }

    public void setOprName(String oprName) {
        this.oprName = oprName;
    }

    public String getOpTime() {
        return opTime;
    }

    public void setOpTime(String opTime) {
        this.opTime = opTime;
    }

    public Long getChkrId() {
        return chkrId;
    }

    public void setChkrId(Long chkrId) {
        this.chkrId = chkrId;
    }

    public String getChkrNum() {
        return chkrNum;
    }

    public void setChkrNum(String chkrNum) {
        this.chkrNum = chkrNum;
    }

    public String getChkrName() {
        return chkrName;
    }

    public void setChkrName(String chkrName) {
        this.chkrName = chkrName;
    }

    public String getChkTime() {
        return chkTime;
    }

    public void setChkTime(String chkTime) {
        this.chkTime = chkTime;
    }

    public String getEffective() {
        return effective;
    }

    public void setEffective(String effective) {
        this.effective = effective;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getCancelled() {
        return cancelled;
    }

    public void setCancelled(String cancelled) {
        this.cancelled = cancelled;
    }

    public String getSuspended() {
        return suspended;
    }

    public void setSuspended(String suspended) {
        this.suspended = suspended;
    }

    public String getExecRtcNum() {
        return execRtcNum;
    }

    public void setExecRtcNum(String execRtcNum) {
        this.execRtcNum = execRtcNum;
    }

    public String getItmdRtcNum() {
        return itmdRtcNum;
    }

    public void setItmdRtcNum(String itmdRtcNum) {
        this.itmdRtcNum = itmdRtcNum;
    }

    public String getStRtcNum() {
        return stRtcNum;
    }

    public void setStRtcNum(String stRtcNum) {
        this.stRtcNum = stRtcNum;
    }

    public String getEndRtcNum() {
        return endRtcNum;
    }

    public void setEndRtcNum(String endRtcNum) {
        this.endRtcNum = endRtcNum;
    }

    public String getAcReqd() {
        return acReqd;
    }

    public void setAcReqd(String acReqd) {
        this.acReqd = acReqd;
    }

    public String getAcBnd() {
        return acBnd;
    }

    public void setAcBnd(String acBnd) {
        this.acBnd = acBnd;
    }

    public Long getAcBndrId() {
        return acBndrId;
    }

    public void setAcBndrId(Long acBndrId) {
        this.acBndrId = acBndrId;
    }

    public String getAcBndrNum() {
        return acBndrNum;
    }

    public void setAcBndrNum(String acBndrNum) {
        this.acBndrNum = acBndrNum;
    }

    public String getAcBndrName() {
        return acBndrName;
    }

    public void setAcBndrName(String acBndrName) {
        this.acBndrName = acBndrName;
    }

    public String getAcBndTime() {
        return acBndTime;
    }

    public void setAcBndTime(String acBndTime) {
        this.acBndTime = acBndTime;
    }

    public String getAfReqd() {
        return afReqd;
    }

    public void setAfReqd(String afReqd) {
        this.afReqd = afReqd;
    }

    public String getAfBnd() {
        return afBnd;
    }

    public void setAfBnd(String afBnd) {
        this.afBnd = afBnd;
    }

    public Long getAfBndrId() {
        return afBndrId;
    }

    public void setAfBndrId(Long afBndrId) {
        this.afBndrId = afBndrId;
    }

    public String getAfBndrNum() {
        return afBndrNum;
    }

    public void setAfBndrNum(String afBndrNum) {
        this.afBndrNum = afBndrNum;
    }

    public String getAfBndrName() {
        return afBndrName;
    }

    public void setAfBndrName(String afBndrName) {
        this.afBndrName = afBndrName;
    }

    public String getAfBndTime() {
        return afBndTime;
    }

    public void setAfBndTime(String afBndTime) {
        this.afBndTime = afBndTime;
    }

    public String getSrFwdd() {
        return srFwdd;
    }

    public void setSrFwdd(String srFwdd) {
        this.srFwdd = srFwdd;
    }

    public String getPrFwdd() {
        return prFwdd;
    }

    public void setPrFwdd(String prFwdd) {
        this.prFwdd = prFwdd;
    }

    public String getRtcNum() {
        return rtcNum;
    }

    public void setRtcNum(String rtcNum) {
        this.rtcNum = rtcNum;
        super.setDtlNum(rtcNum);
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Long getTasksInDeliv() {
        return tasksInDeliv;
    }

    public void setTasksInDeliv(Long tasksInDeliv) {
        this.tasksInDeliv = tasksInDeliv;
    }

    public Long getTasksInRcv() {
        return tasksInRcv;
    }

    public void setTasksInRcv(Long tasksInRcv) {
        this.tasksInRcv = tasksInRcv;
    }

    public List<RtcDtlVo> getRtcDtlList() {
        return rtcDtlList;
    }

    public void setRtcDtlList(List<RtcDtlVo> rtcDtlList) {
        this.rtcDtlList = rtcDtlList;
    }

    public Long getVendeeId() {
        return vendeeId;
    }

    public void setVendeeId(Long vendeeId) {
        this.vendeeId = vendeeId;
    }

    public String getVendeeNum() {
        return vendeeNum;
    }

    public void setVendeeNum(String vendeeNum) {
        this.vendeeNum = vendeeNum;
    }

    public String getVendeeName() {
        return vendeeName;
    }

    public void setVendeeName(String vendeeName) {
        this.vendeeName = vendeeName;
    }


}
