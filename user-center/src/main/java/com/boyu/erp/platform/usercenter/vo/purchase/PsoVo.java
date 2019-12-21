package com.boyu.erp.platform.usercenter.vo.purchase;


import com.boyu.erp.platform.usercenter.utils.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Classname PsoVo
 * @Description TODO
 * @Date 2019/7/17 18:28
 * @Created wz
 */
public class PsoVo extends Purchase implements Serializable {

    /**
     * 购销单号
     */
    private String psoNum;

    /**
     * 购销合同号
     */
    private String pscNum;

    /**
     * 收货仓库id
     */
    private Integer rcvWarehId;

    /**
     * 发货仓库id
     */
    private Integer delivWarehId;

    /**
     * 购销单性质("t","p")
     */
    private String psoKind;

    /**
     * 采购单已生成("t","f")
     */
    private String puoGen;

    /**
     * 销售单已生成("t","f")
     */
    private String sloGen;

    /**
     * 货期
     */
    private String reqdDate;

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
    private Integer ttlDelivBox;

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
    private Integer ttlRcvBox;

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
     * 操作员id
     */
    private Integer oprId;
    /**
     * 操作员代码
     */
    private String oprCode;
    /**
     * 操作员姓名
     */
    private String oprName;

    /**
     * 操作时间
     */
    private String opTime;

    /**
     * 已生效("t","f")
     */
    private String effective;

    /**
     * 进度("pg","cn","rk","ek","ck","dg","dd","rg","rd")
     */
    private String progress;

    /**
     * 撤销("t","f")
     */
    private String cancelled;

    /**
     * 执行单据编号
     */
    private String execPsoNum;

    /**
     * 居间单据编号
     */
    private String itmdPsoNum;

    /**
     * 始发单据编号
     */
    private String stPsoNum;

    /**
     * 转送单据编号
     */
    private String endPsoNum;

    /**
     * 采购单号
     */
    private String puoNum;

    /**
     * 采购合同号
     */
    private String pucNum;

    /**
     * 销售单号
     */
    private String sloNum;

    /**
     * 销售合同号
     */
    private String slcNum;

    /**
     * 收获仓库编号
     */
    private String rcvWarehNum;

    /**
     * 收获仓库名称
     */
    private String rcvWarehName;

    /**
     * 供应商编号
     */
    private String venderNum;
    /**
     * 供应商名称
     */
    private String venderName;

    /**
     * 采购商编号
     */
    private String vendeeNum;
    /**
     * 采购商名称
     */
    private String vendeeName;

    /**
     * 发货仓库编号
     */
    private String delivWarehNum;

    /**
     * 发货仓库名称
     */
    private String delivWarehName;

    /**
     * 供应商介入
     */
    private String vdrInvd;

    /**
     * 采购商介入
     */
    private String vdeInvd;

    /**
     * 挂起
     */
    private String suspended;

    /**
     * 计划控制
     */
    private String planCtrl;

    /**
     * 控制范围sql 拼接map
     */
    private Map<String, Object> map;

    /**
     * 搜索条件 开始日期
     */
    private String startTime;

    /**
     * 搜索条件 结束日期
     */
    private String endTime;

    /**
     * 供应商id
     */

    private Long venderId;

    /**
     * 采购商id
     */
    private Long vendeeId;

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
     * 批量添加明细
     */
    private List<PsoDtlVo> psoDtlList;

    /**
     * 销售桥接方式("d","t")
     */
    private String slBrdgMode;
    /**
     * 采购桥接方式("d","t")
     */
    private String puBrdgMode;

    private BigDecimal exblRate;

    public BigDecimal getExblRate() {
        return exblRate;
    }

    public void setExblRate(BigDecimal exblRate) {
        this.exblRate = exblRate;
    }

    public String getPsoNum() {
        return psoNum;
    }

    public void setPsoNum(String psoNum) {
        this.psoNum = psoNum;
        super.setDtlNum(psoNum);
    }

    public String getPscNum() {
        return pscNum;
    }

    public void setPscNum(String pscNum) {
        this.pscNum = pscNum;
    }

    public Integer getRcvWarehId() {
        return rcvWarehId;
    }

    public void setRcvWarehId(Integer rcvWarehId) {
        this.rcvWarehId = rcvWarehId;
    }

    public Integer getDelivWarehId() {
        return delivWarehId;
    }

    public void setDelivWarehId(Integer delivWarehId) {
        this.delivWarehId = delivWarehId;
    }

    public String getPsoKind() {
        return psoKind;
    }

    public void setPsoKind(String psoKind) {
        this.psoKind = psoKind;
    }

    public String getPuoGen() {
        return puoGen;
    }

    public void setPuoGen(String puoGen) {
        this.puoGen = puoGen;
    }

    public String getSloGen() {
        return sloGen;
    }

    public void setSloGen(String sloGen) {
        this.sloGen = sloGen;
    }

    public String getReqdDate() {
        return reqdDate;
    }

    public void setReqdDate(String reqdDate) {
        this.reqdDate = reqdDate;
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

    public Integer getTtlDelivBox() {
        return ttlDelivBox;
    }

    public void setTtlDelivBox(Integer ttlDelivBox) {
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

    public Integer getTtlRcvBox() {
        return ttlRcvBox;
    }

    public void setTtlRcvBox(Integer ttlRcvBox) {
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

    public Integer getOprId() {
        return oprId;
    }

    public void setOprId(Integer oprId) {
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

    public String getExecPsoNum() {
        return execPsoNum;
    }

    public void setExecPsoNum(String execPsoNum) {
        this.execPsoNum = execPsoNum;
    }

    public String getItmdPsoNum() {
        return itmdPsoNum;
    }

    public void setItmdPsoNum(String itmdPsoNum) {
        this.itmdPsoNum = itmdPsoNum;
    }

    public String getStPsoNum() {
        return stPsoNum;
    }

    public void setStPsoNum(String stPsoNum) {
        this.stPsoNum = stPsoNum;
    }

    public String getEndPsoNum() {
        return endPsoNum;
    }

    public void setEndPsoNum(String endPsoNum) {
        this.endPsoNum = endPsoNum;
    }

    public String getPuoNum() {
        return puoNum;
    }

    public void setPuoNum(String puoNum) {
        this.puoNum = puoNum;
        if(StringUtils.isNotEmpty(puoNum)){
            super.setDocNum(puoNum);
        }
    }

    public String getPucNum() {
        return pucNum;
    }

    public void setPucNum(String pucNum) {
        this.pucNum = pucNum;
    }

    public String getSloNum() {
        return sloNum;
    }

    public void setSloNum(String sloNum) {
        this.sloNum = sloNum;
        if(StringUtils.isNotEmpty(sloNum)){
            super.setDocNum(sloNum);
        }
    }

    public String getSlcNum() {
        return slcNum;
    }

    public void setSlcNum(String slcNum) {
        this.slcNum = slcNum;
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

    public String getVdrInvd() {
        return vdrInvd;
    }

    public void setVdrInvd(String vdrInvd) {
        this.vdrInvd = vdrInvd;
    }

    public String getVdeInvd() {
        return vdeInvd;
    }

    public void setVdeInvd(String vdeInvd) {
        this.vdeInvd = vdeInvd;
    }

    public String getSuspended() {
        return suspended;
    }

    public void setSuspended(String suspended) {
        this.suspended = suspended;
    }

    public String getPlanCtrl() {
        return planCtrl;
    }

    public void setPlanCtrl(String planCtrl) {
        this.planCtrl = planCtrl;
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

    public List<PsoDtlVo> getPsoDtlList() {
        return psoDtlList;
    }

    public void setPsoDtlList(List<PsoDtlVo> psoDtlList) {
        this.psoDtlList = psoDtlList;
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

    public PsoVo() {
    }

    public PsoVo(String psoNum) {
        this.psoNum = psoNum;
    }

    public PsoVo(String puoNum, Long unitId) {
        this.puoNum = puoNum;
        super.setUnitId(unitId);
    }

    public PsoVo(String puoNum, String sloNum) {
        this.puoNum = puoNum;
        this.sloNum = sloNum;
    }

    public PsoVo(String puoNum, String sloNum, String unitId) {
        this.puoNum = puoNum;
        this.sloNum = sloNum;
        super.setUnitId(Long.valueOf(unitId));
    }
}
