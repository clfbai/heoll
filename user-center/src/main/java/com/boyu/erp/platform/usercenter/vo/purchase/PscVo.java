package com.boyu.erp.platform.usercenter.vo.purchase;


import com.boyu.erp.platform.usercenter.utils.StringUtils;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Classname PscVo
 * @Description TODO
 * @Date 2019/7/4 19:06
 * @Created wz
 */
@ToString
public class PscVo extends Purchase implements Serializable {

    /**
     * 采购合同号
     */
    private String pucNum;
    /**
     * 销售合同号
     */
    private String slcNum;
    /**
     * 购销合同号
     */
    private String pscNum;
    /**
     * 采购合同类别
     */
    private String pucType;
    /**
     * 销售合同类别
     */
    private String slcType;
    /**
     * 购销合同类别
     */
    private String pscType;
    /**
     * 供应商ID
     */
    private String venderId;
    /**
     * 供应商编号
     */
    private String venderNum;
    /**
     * 供应商名称
     */
    private String venderName;
    /**
     * 采购商id
     */
    private String vendeeId;
    /**
     * 采购商编号
     */
    private String vendeeNum;
    /**
     * 采购商名称
     */
    private String vendeeName;
    /**
     * 协议控制方
     */
    private String psaCtlr;
    /**
     * 供应商仓库ID
     */
    private String vdrWarehId;
    /**
     * 供应商仓库编号
     */
    private String vdrWarehNum;
    /**
     * 供应商仓库名称
     */
    private String vdrWarehName;
    /**
     * 采购商仓库ID
     */
    private String vdeWarehId;
    /**
     * 采购商仓库编号
     */
    private String vdeWarehNum;
    /**
     * 采购商仓库名称
     */
    private String vdeWarehName;
    /**
     * 始发方ID
     */
    private String stUnitId;
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
    private String stWarehId;
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
    private String endUnitId;
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
    private String endWarehId;
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
     * 供应商会计组织id
     */
    private String vdrFsclUnitId;
    /**
     * 供应商会计组织代码
     */
    private String vdrFsclUnitCode;
    /**
     * 供应商会计组织名称
     */
    private String vdrFsclUnitName;
    /**
     * 采购商会计组织id
     */
    private String vdeFsclUnitId;
    /**
     * 采购商会计组织代码
     */
    private String vdeFsclUnitCode;
    /**
     * 采购商会计组织名称
     */
    private String vdeFsclUnitName;
    /**
     * 采购商介入("t","f")
     */
    private String vdeInvd;
    /**
     * 销售合同自动生成("t","f")
     */
    private String slcAutoGen;
    /**
     * 销售合同自动审核("t","f")
     */
    private String slcAutoChk;
    /**
     * 采购合同自动生成("t","f")
     */
    private String pucAutoGen;
    /**
     * 采购合同已生成("t","f")
     */
    private String pucGen;
    /**
     * 销售合同已生成("t","f")
     */
    private String slcGen;
    /**
     * 采购合同自动审核("t","f")
     */
    private String pucAutoChk;
    /**
     * 是否居间销售("t","f")
     */
    private String isSlItmd;
    /**
     * 是否居间采购("t","f")
     */
    private String isPuItmd;
    /**
     * 销售桥接方式("d","t")
     */
    private String slBrdgMode;
    /**
     * 采购桥接方式("d","t")
     */
    private String puBrdgMode;
    /**
     * 定价点("ct","dl","rc")
     */
    private String prcSite;
    /**
     * 是否代销("t","f")
     */
    private String isCms;
    /**
     * 差异裁定方("d","r")
     */
    private String drDiffJgd;
    /**
     * 多次执行("t","f")
     */
    private String multiImpl;
    /**
     * 按指令执行("t","f")
     */
    private String implByIns;
    /**
     * 是否现货("t","f")
     */
    private String isSpot;
    /**
     * 货期控制("sg","pd","SalesOrder")
     */
    private String rqdCtrl;
    /**
     * 货期
     */
    private String reqdDate;
    /**
     * 结算方式("dp","pa","pp")
     */
    private String psStlMthd;
    /**
     * 允许增补商品("t","f")
     */
    private String splEnabled;
    /**
     * 定金
     */
    private BigDecimal deposit;
    /**
     * 启用冻款("t","f")
     */
    private String mfzEnabled;
    /**
     * 启用保证金("t","f")
     */
    private String gmEnabled;
    /**
     * 保证金
     */
    private BigDecimal guaMon;
    /**
     * 超额许可比例
     */
    private BigDecimal exblRate;
    /**
     * 冻款点("ck","ad")
     */
    private String psMfzSite;
    /**
     * 原始单据类别
     */
    private String srcDocType;
    /**
        * 原始单据组织ID
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
     * 订单类别
     */
    private String ordType;

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
     * 退销总数量
     */
    private BigDecimal ttlRsQty;

    /**
     * 退销总箱数
     */
    private Long ttlRsBox;

    /**
     * 退销总金额
     */
    private BigDecimal ttlRsVal;

    /**
     * 退销总税款
     */
    private BigDecimal ttlRsTax;

    /**
     * 退销总市值
     */
    private BigDecimal ttlRsMkv;

    /**
     * 退购总数量
     */
    private BigDecimal ttlRpQty;

    /**
     * 退购总箱数
     */
    private Long ttlRpBox;

    /**
     * 退购总金额
     */
    private BigDecimal ttlRpVal;

    /**
     * 退购总税款
     */
    private BigDecimal ttlRpTax;

    /**
     * 退购总市值
     */
    private BigDecimal ttlRpMkv;
    /**
     * 发货中任务数
     */
    private Long tasksInDeliv;

    /**
     * 收货中任务数
     */
    private Long tasksInRcv;
    /**
     * 预期总返利
     */
    private BigDecimal ttlExpdRwd;

    /**
     * 发货总返利
     */
    private BigDecimal ttlDelivRwd;

    /**
     * 到货总返利
     */
    private BigDecimal ttlRcvRwd;
    /**
     * 操作员id
     */
    private String oprId;
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
    private String chkrId;
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
     * 进度
     */
    private String progress;
    /**
     * 挂起("t","f")
     */
    private String suspended;
    /**
     * 撤销("t","f")
     */
    private String cancelled;

    /**
     * 已递延("t","f")
     */
    private String renewed;
    /**
     * 执行合同编号
     */
    private String execPscNum;

    /**
     * 居间合同编号
     */
    private String itmdPscNum;

    /**
     * 始发合同编号
     */
    private String stPscNum;

    /**
     * 转送合同编号
     */
    private String endPscNum;
    /**
     * 绑定附加成本("t","f")
     */
    private String acReqd;
    /**
     * 绑定附加成本("t","f")
     */
    private String afReqd;
    /**
     * 计划控制("t","f")
     */
    private String planCtrl;

    /**
     * 展会编号
     */
    private String tfrNum;
    /**
     * 附加成本已绑定("t","f")
     */
    private String acBnd;
    /**
     * 附加成本绑定人id
     */
    private String acBndrId;

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
     * 销售控制("i","p","f")
     */
    private String slCtrl;

    /**
     * 已转销("t","f")
     */
    private String slFwdd;
    /**
     * 附加费用已绑定("t","f")
     */
    private String afBnd;
    /**
     * 附加费用绑定人id
     */
    private String afBndrId;
    /**
     * 附加费用绑定人编号
     */
    private String afBndrNum;
    /**
     * 附加费用绑定人名称
     */
    private String afBndrName;
    /**
     * 附加费用绑定时间
     */
    private String afBndTime;
    /**
     * 采购控制("i","p","f")
     */
    private String puCtrl;

    /**
     * 已转购("t","f")
     */
    private String puFwdd;
    /**
     * 基准合同编号
     */
    private String basePscNum;
    /**
     * 基准采购编号
     */
    private String basePucNum;

    /**
     * 基准销售编号
     */
    private String baseSlcNum;

    /**
     * 搜索条件 开始日期
     */
    private String startTime;
    /**
     * 搜索条件结束日期
     */
    private String endTime;
    /**
     * 供应商是否介入
     */
    private String vdrInvd;

    /**
     * 判断货期是否修改   默认是0  改值变1
     */
    private String reqdType = "0";
    /**
     * 货期控制修改前的值
     */
    private String rqdCtrlOld;

    /**
     * 批量添加明细
     */
    private List<PscDtlVo> pscDtlList;




    public String getPucNum() {
        return pucNum;
    }

    public void setPucNum(String pucNum) {
        this.pucNum = pucNum;
        if(StringUtils.isNotEmpty(pucNum)){
            super.setDocNum(pucNum);
        }
    }

    public String getSlcNum() {
        return slcNum;
    }

    public void setSlcNum(String slcNum) {
        this.slcNum = slcNum;
        if(StringUtils.isNotEmpty(slcNum)){
            super.setDocNum(slcNum);
        }
    }

    public String getPscNum() {
        return pscNum;
    }

    public void setPscNum(String pscNum) {
        super.setDtlNum(pscNum);
        this.pscNum = pscNum;
    }

    public String getPucType() {
        return pucType;
    }

    public void setPucType(String pucType) {
        this.pucType = pucType;
    }

    public String getSlcType() {
        return slcType;
    }

    public void setSlcType(String slcType) {
        this.slcType = slcType;
    }

    public String getPscType() {
        return pscType;
    }

    public void setPscType(String pscType) {
        this.pscType = pscType;
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

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
    }

    public String getVendeeId() {
        return vendeeId;
    }

    public void setVendeeId(String vendeeId) {
        this.vendeeId = vendeeId;
    }

    public String getPsaCtlr() {
        return psaCtlr;
    }

    public void setPsaCtlr(String psaCtlr) {
        this.psaCtlr = psaCtlr;
    }

    public String getVdrWarehId() {
        return vdrWarehId;
    }

    public void setVdrWarehId(String vdrWarehId) {
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

    public String getVdeWarehId() {
        return vdeWarehId;
    }

    public void setVdeWarehId(String vdeWarehId) {
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

    public String getStUnitId() {
        return stUnitId;
    }

    public void setStUnitId(String stUnitId) {
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

    public String getStWarehId() {
        return stWarehId;
    }

    public void setStWarehId(String stWarehId) {
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

    public String getEndUnitId() {
        return endUnitId;
    }

    public void setEndUnitId(String endUnitId) {
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

    public String getEndWarehId() {
        return endWarehId;
    }

    public void setEndWarehId(String endWarehId) {
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

    public String getVdrFsclUnitId() {
        return vdrFsclUnitId;
    }

    public void setVdrFsclUnitId(String vdrFsclUnitId) {
        this.vdrFsclUnitId = vdrFsclUnitId;
    }

    public String getVdrFsclUnitName() {
        return vdrFsclUnitName;
    }

    public void setVdrFsclUnitName(String vdrFsclUnitName) {
        this.vdrFsclUnitName = vdrFsclUnitName;
    }

    public String getVdeFsclUnitId() {
        return vdeFsclUnitId;
    }

    public void setVdeFsclUnitId(String vdeFsclUnitId) {
        this.vdeFsclUnitId = vdeFsclUnitId;
    }

    public String getVdeFsclUnitName() {
        return vdeFsclUnitName;
    }

    public void setVdeFsclUnitName(String vdeFsclUnitName) {
        this.vdeFsclUnitName = vdeFsclUnitName;
    }

    public String getVdeInvd() {
        return vdeInvd;
    }

    public void setVdeInvd(String vdeInvd) {
        this.vdeInvd = vdeInvd;
    }

    public String getSlcAutoGen() {
        return slcAutoGen;
    }

    public void setSlcAutoGen(String slcAutoGen) {
        this.slcAutoGen = slcAutoGen;
    }

    public String getSlcAutoChk() {
        return slcAutoChk;
    }

    public void setSlcAutoChk(String slcAutoChk) {
        this.slcAutoChk = slcAutoChk;
    }

    public String getPucAutoGen() {
        return pucAutoGen;
    }

    public void setPucAutoGen(String pucAutoGen) {
        this.pucAutoGen = pucAutoGen;
    }

    public String getPucGen() {
        return pucGen;
    }

    public void setPucGen(String pucGen) {
        this.pucGen = pucGen;
    }

    public String getSlcGen() {
        return slcGen;
    }

    public void setSlcGen(String slcGen) {
        this.slcGen = slcGen;
    }

    public String getPucAutoChk() {
        return pucAutoChk;
    }

    public void setPucAutoChk(String pucAutoChk) {
        this.pucAutoChk = pucAutoChk;
    }

    public String getIsSlItmd() {
        return isSlItmd;
    }

    public void setIsSlItmd(String isSlItmd) {
        this.isSlItmd = isSlItmd;
    }

    public String getIsPuItmd() {
        return isPuItmd;
    }

    public void setIsPuItmd(String isPuItmd) {
        this.isPuItmd = isPuItmd;
    }

    public String getSlBrdgMode() {
        return slBrdgMode;
    }

    public void setSlBrdgMode(String slBrdgMode) {
        this.slBrdgMode = slBrdgMode;
        if(StringUtils.isNotEmpty(slBrdgMode)){
            super.setBrdgMode(slBrdgMode);
        }

    }

    public String getPuBrdgMode() {
        return puBrdgMode;
    }

    public void setPuBrdgMode(String puBrdgMode) {
        this.puBrdgMode = puBrdgMode;
        if(StringUtils.isNotEmpty(puBrdgMode)){
            super.setBrdgMode(puBrdgMode);
        }
    }

    public String getPrcSite() {
        return prcSite;
    }

    public void setPrcSite(String prcSite) {
        this.prcSite = prcSite;
    }

    public String getIsCms() {
        return isCms;
    }

    public void setIsCms(String isCms) {
        this.isCms = isCms;
    }

    public String getDrDiffJgd() {
        return drDiffJgd;
    }

    public void setDrDiffJgd(String drDiffJgd) {
        this.drDiffJgd = drDiffJgd;
    }

    public String getMultiImpl() {
        return multiImpl;
    }

    public void setMultiImpl(String multiImpl) {
        this.multiImpl = multiImpl;
    }

    public String getImplByIns() {
        return implByIns;
    }

    public void setImplByIns(String implByIns) {
        this.implByIns = implByIns;
    }

    public String getIsSpot() {
        return isSpot;
    }

    public void setIsSpot(String isSpot) {
        this.isSpot = isSpot;
    }

    public String getRqdCtrl() {
        return rqdCtrl;
    }

    public void setRqdCtrl(String rqdCtrl) {
        this.rqdCtrl = rqdCtrl;
    }

    public String getReqdDate() {
        return reqdDate;
    }

    public void setReqdDate(String reqdDate) {
        this.reqdDate = reqdDate;
    }

    public String getPsStlMthd() {
        return psStlMthd;
    }

    public void setPsStlMthd(String psStlMthd) {
        this.psStlMthd = psStlMthd;
    }

    public String getSplEnabled() {
        return splEnabled;
    }

    public void setSplEnabled(String splEnabled) {
        this.splEnabled = splEnabled;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public String getMfzEnabled() {
        return mfzEnabled;
    }

    public void setMfzEnabled(String mfzEnabled) {
        this.mfzEnabled = mfzEnabled;
    }

    public String getGmEnabled() {
        return gmEnabled;
    }

    public void setGmEnabled(String gmEnabled) {
        this.gmEnabled = gmEnabled;
    }

    public BigDecimal getGuaMon() {
        return guaMon;
    }

    public void setGuaMon(BigDecimal guaMon) {
        this.guaMon = guaMon;
    }

    public BigDecimal getExblRate() {
        return exblRate;
    }

    public void setExblRate(BigDecimal exblRate) {
        this.exblRate = exblRate;
    }

    public String getPsMfzSite() {
        return psMfzSite;
    }

    public void setPsMfzSite(String psMfzSite) {
        this.psMfzSite = psMfzSite;
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

    public String getOrdType() {
        return ordType;
    }

    public void setOrdType(String ordType) {
        this.ordType = ordType;
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

    public BigDecimal getTtlRsQty() {
        return ttlRsQty;
    }

    public void setTtlRsQty(BigDecimal ttlRsQty) {
        this.ttlRsQty = ttlRsQty;
    }

    public Long getTtlRsBox() {
        return ttlRsBox;
    }

    public void setTtlRsBox(Long ttlRsBox) {
        this.ttlRsBox = ttlRsBox;
    }

    public BigDecimal getTtlRsVal() {
        return ttlRsVal;
    }

    public void setTtlRsVal(BigDecimal ttlRsVal) {
        this.ttlRsVal = ttlRsVal;
    }

    public BigDecimal getTtlRsTax() {
        return ttlRsTax;
    }

    public void setTtlRsTax(BigDecimal ttlRsTax) {
        this.ttlRsTax = ttlRsTax;
    }

    public BigDecimal getTtlRsMkv() {
        return ttlRsMkv;
    }

    public void setTtlRsMkv(BigDecimal ttlRsMkv) {
        this.ttlRsMkv = ttlRsMkv;
    }

    public BigDecimal getTtlRpQty() {
        return ttlRpQty;
    }

    public void setTtlRpQty(BigDecimal ttlRpQty) {
        this.ttlRpQty = ttlRpQty;
    }

    public Long getTtlRpBox() {
        return ttlRpBox;
    }

    public void setTtlRpBox(Long ttlRpBox) {
        this.ttlRpBox = ttlRpBox;
    }

    public BigDecimal getTtlRpVal() {
        return ttlRpVal;
    }

    public void setTtlRpVal(BigDecimal ttlRpVal) {
        this.ttlRpVal = ttlRpVal;
    }

    public BigDecimal getTtlRpTax() {
        return ttlRpTax;
    }

    public void setTtlRpTax(BigDecimal ttlRpTax) {
        this.ttlRpTax = ttlRpTax;
    }

    public BigDecimal getTtlRpMkv() {
        return ttlRpMkv;
    }

    public void setTtlRpMkv(BigDecimal ttlRpMkv) {
        this.ttlRpMkv = ttlRpMkv;
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

    public BigDecimal getTtlExpdRwd() {
        return ttlExpdRwd;
    }

    public void setTtlExpdRwd(BigDecimal ttlExpdRwd) {
        this.ttlExpdRwd = ttlExpdRwd;
    }

    public BigDecimal getTtlDelivRwd() {
        return ttlDelivRwd;
    }

    public void setTtlDelivRwd(BigDecimal ttlDelivRwd) {
        this.ttlDelivRwd = ttlDelivRwd;
    }

    public BigDecimal getTtlRcvRwd() {
        return ttlRcvRwd;
    }

    public void setTtlRcvRwd(BigDecimal ttlRcvRwd) {
        this.ttlRcvRwd = ttlRcvRwd;
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

    public String getChkrId() {
        return chkrId;
    }

    public void setChkrId(String chkrId) {
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

    public String getSuspended() {
        return suspended;
    }

    public void setSuspended(String suspended) {
        this.suspended = suspended;
    }

    public String getCancelled() {
        return cancelled;
    }

    public void setCancelled(String cancelled) {
        this.cancelled = cancelled;
    }

    public String getRenewed() {
        return renewed;
    }

    public void setRenewed(String renewed) {
        this.renewed = renewed;
    }

    public String getExecPscNum() {
        return execPscNum;
    }

    public void setExecPscNum(String execPscNum) {
        this.execPscNum = execPscNum;
    }

    public String getItmdPscNum() {
        return itmdPscNum;
    }

    public void setItmdPscNum(String itmdPscNum) {
        this.itmdPscNum = itmdPscNum;
    }

    public String getStPscNum() {
        return stPscNum;
    }

    public void setStPscNum(String stPscNum) {
        this.stPscNum = stPscNum;
    }

    public String getEndPscNum() {
        return endPscNum;
    }

    public void setEndPscNum(String endPscNum) {
        this.endPscNum = endPscNum;
    }

    public String getAcReqd() {
        return acReqd;
    }

    public void setAcReqd(String acReqd) {
        this.acReqd = acReqd;
    }

    public String getAfReqd() {
        return afReqd;
    }

    public void setAfReqd(String afReqd) {
        this.afReqd = afReqd;
    }

    public String getPlanCtrl() {
        return planCtrl;
    }

    public void setPlanCtrl(String planCtrl) {
        this.planCtrl = planCtrl;
    }

    public String getTfrNum() {
        return tfrNum;
    }

    public void setTfrNum(String tfrNum) {
        this.tfrNum = tfrNum;
    }

    public String getAcBnd() {
        return acBnd;
    }

    public void setAcBnd(String acBnd) {
        this.acBnd = acBnd;
    }

    public String getAcBndrId() {
        return acBndrId;
    }

    public void setAcBndrId(String acBndrId) {
        this.acBndrId = acBndrId;
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

    public String getSlCtrl() {
        return slCtrl;
    }

    public void setSlCtrl(String slCtrl) {
        this.slCtrl = slCtrl;
    }

    public String getSlFwdd() {
        return slFwdd;
    }

    public void setSlFwdd(String slFwdd) {
        this.slFwdd = slFwdd;
    }

    public String getAfBnd() {
        return afBnd;
    }

    public void setAfBnd(String afBnd) {
        this.afBnd = afBnd;
    }

    public String getAfBndrId() {
        return afBndrId;
    }

    public void setAfBndrId(String afBndrId) {
        this.afBndrId = afBndrId;
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

    public String getPuCtrl() {
        return puCtrl;
    }

    public void setPuCtrl(String puCtrl) {
        this.puCtrl = puCtrl;
    }

    public String getPuFwdd() {
        return puFwdd;
    }

    public void setPuFwdd(String puFwdd) {
        this.puFwdd = puFwdd;
    }

    public String getBasePscNum() {
        return basePscNum;
    }

    public void setBasePscNum(String basePscNum) {
        this.basePscNum = basePscNum;
    }

    public String getBasePucNum() {
        return basePucNum;
    }

    public void setBasePucNum(String basePucNum) {
        this.basePucNum = basePucNum;
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

    public String getVdrInvd() {
        return vdrInvd;
    }

    public void setVdrInvd(String vdrInvd) {
        this.vdrInvd = vdrInvd;
    }

    public String getReqdType() {
        return reqdType;
    }

    public void setReqdType(String reqdType) {
        this.reqdType = reqdType;
    }

    public String getRqdCtrlOld() {
        return rqdCtrlOld;
    }

    public void setRqdCtrlOld(String rqdCtrlOld) {
        this.rqdCtrlOld = rqdCtrlOld;
    }

    public List<PscDtlVo> getPscDtlList() {
        return pscDtlList;
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

    public String getVdrFsclUnitCode() {
        return vdrFsclUnitCode;
    }

    public void setVdrFsclUnitCode(String vdrFsclUnitCode) {
        this.vdrFsclUnitCode = vdrFsclUnitCode;
    }

    public String getVdeFsclUnitCode() {
        return vdeFsclUnitCode;
    }

    public void setVdeFsclUnitCode(String vdeFsclUnitCode) {
        this.vdeFsclUnitCode = vdeFsclUnitCode;
    }

    public void setPscDtlList(List<PscDtlVo> pscDtlList) {
        this.pscDtlList = pscDtlList;
    }

    public String getAcBndrNum() {
        return acBndrNum;
    }

    public void setAcBndrNum(String acBndrNum) {
        this.acBndrNum = acBndrNum;
    }

    public String getAfBndrNum() {
        return afBndrNum;
    }

    public void setAfBndrNum(String afBndrNum) {
        this.afBndrNum = afBndrNum;
    }

    public String getBaseSlcNum() {
        return baseSlcNum;
    }

    public void setBaseSlcNum(String baseSlcNum) {
        this.baseSlcNum = baseSlcNum;
    }

    public PscVo() {
    }

    public PscVo(String pscNum) {
        this.pscNum = pscNum;
    }

    public PscVo(String pucNum, String slcNum) {
        this.pucNum = pucNum;
        this.slcNum = slcNum;
    }
    public PscVo(String pucNum,String slcNum,String unitId) {
        this.pucNum = pucNum;
        this.slcNum = slcNum;
        super.setUnitId(Long.valueOf(unitId));
    }

    public PscVo(String pscNum, String rqdCtrl, List<PscDtlVo> pscDtlList) {
        this.pscNum = pscNum;
        this.rqdCtrl = rqdCtrl;
        this.pscDtlList = pscDtlList;
    }
}


