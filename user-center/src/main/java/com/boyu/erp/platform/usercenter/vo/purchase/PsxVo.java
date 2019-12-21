package com.boyu.erp.platform.usercenter.vo.purchase;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Classname PscVo
 * @Description TODO
 * @Date 2019/7/15 16:52
 * @Created wz
 */
public class PsxVo implements Serializable {
    /**
     * 当前组织/权限组织
     */
    private Long sUnitId;
    /**
     * 组织Id
     */
    private Long unitId;
    /**
     * 采购申请号
     */
    private String puaNum;
    /**
     * 销售申请号
     */
    private String slaNum;
    /**
     * 购销申请号
     */
    private String psxNum;
    /**
     * 单据日期
     */
    private String docDate;
    /**
     * 采购申请类别
     */
    private String puaType;
    /**
     * 销售申请类别
     */
    private String slaType;
    /**
     * 购销申请类别
     */
    private String psxType;
    /**
     * 发货仓库ID
     */
    private Long delivWarehId;
    /**
     * 发货仓库编号
     */
    private String delivWarehNum;
    /**
     * 发货仓库名称
     */
    private String delivWarehName;
    /**
     * 部门ID
     */
    private Long deptId;
    /**
     * 部门编号
     */
    private String deptNum;
    /**
     * 部门名称
     */
    private String deptName;
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
     * 采购申请自动生成
     */
    private String puaAutoGen;

    /**
     * 采购申请自动审核
     */
    private String puaAutoChk;
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
     * 收货仓库ID
     */
    private Long rcvWarehId;
    /**
     * 收货仓库编号
     */
    private String rcvWarehNum;
    /**
     * 收货仓库名称
     */
    private String rcvWarehName;
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
     * 供应商介入
     */
    private String vdrInvd;
    /**
     * 销售申请自动生成
     */
    private String slaAutoGen;

    /**
     * 销售申请已生成
     */
    private String slaGen;

    /**
     * 销售申请自动审核
     */
    private String slaAutoChk;
    /**
     * 货期控制
     */
    private String rqdCtrl;
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
     * 货期
     */
    private String reqdDate;
    /**
     * 启用配码
     */
    private String bxiEnabled;
    /**
     * 总数量
     */
    private BigDecimal ttlQty = new BigDecimal("0");

    /**
     * 总箱数
     */
    private Long ttlBox= 0L;

    /**
     * 总金额
     */
    private BigDecimal ttlVal= new BigDecimal("0");

    /**
     * 总市值
     */
    private BigDecimal ttlMkv= new BigDecimal("0");
    /**
     * 操作员ID
     */
    private Long oprId;
    /**
     * 操作员代码
     */
    private String oprCode;
    /**
     * 审核人姓名
     */
    private String oprName;
    /**
     * 操作时间
     */
    private String opTime;
    /**
     * 审核人ID
     */
    private Long chkrId;
    /**
     * 审核人编号
     */
    private String chkrNum;
    /**
     * 审核人姓名
     */
    private String chkrName;
    /**
     * 审核时间
     */
    private String chkTime;
    /**
     * 已生效
     */
    private String effective;

    /**
     * 进度
     */
    private String progress;
    /**
     * 挂起
     */
    private String suspended;
    /**
     * 撤销
     */
    private String cancelled;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 控制范围sql 拼接map
     */
    private Map<String, Object> map;
    /**
     * 搜索条件 开始日期
     */
    private String startTime;
    /**
     * 搜索条件结束日期
     */
    private String endTime;
    /**
     * 采购商id
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
     * 采购商介入
     */
    private String vdeInvd;
    /**
     * 采购申请已生成
     */
    private String puaGen;

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
    private List<PsxDtlVo> psxDtlList;

    public PsxVo() {
    }

    public PsxVo(Long unitId, String puaNum) {
        this.unitId = unitId;
        this.puaNum = puaNum;
    }

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

    public String getPuaNum() {
        return puaNum;
    }

    public void setPuaNum(String puaNum) {
        this.puaNum = puaNum;
    }

    public String getSlaNum() {
        return slaNum;
    }

    public void setSlaNum(String slaNum) {
        this.slaNum = slaNum;
    }

    public String getPsxNum() {
        return psxNum;
    }

    public void setPsxNum(String psxNum) {
        this.psxNum = psxNum;
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public String getPuaType() {
        return puaType;
    }

    public void setPuaType(String puaType) {
        this.puaType = puaType;
    }

    public String getSlaType() {
        return slaType;
    }

    public void setSlaType(String slaType) {
        this.slaType = slaType;
    }

    public String getPsxType() {
        return psxType;
    }

    public void setPsxType(String psxType) {
        this.psxType = psxType;
    }

    public Long getDelivWarehId() {
        return delivWarehId;
    }

    public void setDelivWarehId(Long delivWarehId) {
        this.delivWarehId = delivWarehId;
    }

    public String getDelivWarehNum() {
        return delivWarehNum;
    }

    public void setDelivWarehNum(String delivWarehNum) {
        this.delivWarehNum = delivWarehNum;
    }

    public String getDelivWarehName() {
        return delivWarehName;
    }

    public void setDelivWarehName(String delivWarehName) {
        this.delivWarehName = delivWarehName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptNum() {
        return deptNum;
    }

    public void setDeptNum(String deptNum) {
        this.deptNum = deptNum;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public String getPuaAutoGen() {
        return puaAutoGen;
    }

    public void setPuaAutoGen(String puaAutoGen) {
        this.puaAutoGen = puaAutoGen;
    }

    public String getPuaAutoChk() {
        return puaAutoChk;
    }

    public void setPuaAutoChk(String puaAutoChk) {
        this.puaAutoChk = puaAutoChk;
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

    public Long getRcvWarehId() {
        return rcvWarehId;
    }

    public void setRcvWarehId(Long rcvWarehId) {
        this.rcvWarehId = rcvWarehId;
    }

    public String getRcvWarehNum() {
        return rcvWarehNum;
    }

    public void setRcvWarehNum(String rcvWarehNum) {
        this.rcvWarehNum = rcvWarehNum;
    }

    public String getRcvWarehName() {
        return rcvWarehName;
    }

    public void setRcvWarehName(String rcvWarehName) {
        this.rcvWarehName = rcvWarehName;
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

    public String getVdrInvd() {
        return vdrInvd;
    }

    public void setVdrInvd(String vdrInvd) {
        this.vdrInvd = vdrInvd;
    }

    public String getSlaAutoGen() {
        return slaAutoGen;
    }

    public void setSlaAutoGen(String slaAutoGen) {
        this.slaAutoGen = slaAutoGen;
    }

    public String getSlaGen() {
        return slaGen;
    }

    public void setSlaGen(String slaGen) {
        this.slaGen = slaGen;
    }

    public String getSlaAutoChk() {
        return slaAutoChk;
    }

    public void setSlaAutoChk(String slaAutoChk) {
        this.slaAutoChk = slaAutoChk;
    }

    public String getRqdCtrl() {
        return rqdCtrl;
    }

    public void setRqdCtrl(String rqdCtrl) {
        this.rqdCtrl = rqdCtrl;
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

    public String getReqdDate() {
        return reqdDate;
    }

    public void setReqdDate(String reqdDate) {
        this.reqdDate = reqdDate;
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

    public BigDecimal getTtlMkv() {
        return ttlMkv;
    }

    public void setTtlMkv(BigDecimal ttlMkv) {
        this.ttlMkv = ttlMkv;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
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

    public String getVdeInvd() {
        return vdeInvd;
    }

    public void setVdeInvd(String vdeInvd) {
        this.vdeInvd = vdeInvd;
    }

    public String getPuaGen() {
        return puaGen;
    }

    public void setPuaGen(String puaGen) {
        this.puaGen = puaGen;
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

    public List<PsxDtlVo> getPsxDtlList() {
        return psxDtlList;
    }

    public void setPsxDtlList(List<PsxDtlVo> psxDtlList) {
        this.psxDtlList = psxDtlList;
    }
}
