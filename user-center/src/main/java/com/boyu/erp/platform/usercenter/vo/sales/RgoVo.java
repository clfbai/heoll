package com.boyu.erp.platform.usercenter.vo.sales;

import com.boyu.erp.platform.usercenter.entity.basic.Ca;
import com.boyu.erp.platform.usercenter.entity.sales.Rgo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname RgoVo
 * @Description TODO
 * @Date 2019/10/6 14:36
 * @Created by wz
 */
public class RgoVo extends Rgo implements Serializable {

    /**
     * 权限
     */
    private Long sUnitId;
    /**
     * 调配单类别别名
     */
    private String rgoTypeCp;

    /**
     * 调出方介入别名
     */
    private String srcUnitInvdCp;

    /**
     * 退购合同自动生成别名
     */
    private String prcAutoGenCp;

    /**
     * 退购合同自动审核别名
     */
    private String prcAutoChkCp;

    /**
     * 调入方介入别名
     */
    private String destUnitInvdCp;

    /**
     * 采购合同自动生成别名
     */
    private String pucAutoGenCp;

    /**
     * 采购合同自动审核别名
     */
    private String pucAutoChkCp;

    /**
     * 差异裁定方别名
     */
    private String drDiffJgdCp;

    /**
     * 启用配码别名
     */
    private String bxiEnabledCp;

    /**
     * 原始单据类别别名
     */
    private String srcDocTypeCp;

    /**
     * 已生效别名
     */
    private String effectiveCp;

    /**
     * 进度别名
     */
    private String progressCp;

    /**
     * 撤销别名
     */
    private String cancelledCp;

    /**
     * 挂起别名
     */
    private String suspendedCp;


    /**
     * 会计组织代码
     */
    private String fsclUnitCode;

    /**
     * 会计组织名称
     */
    private String fsclUnitCp;

    /**
     * 中转仓库编号
     */
    private String tranWarehNum;

    /**
     * 中转仓库名称
     */
    private String tranWarehCp;

    /**
     * 调出方编号
     */
    private String srcUnitNum;

    /**
     * 调出方名称
     */
    private String srcUnitCp;

    /**
     * 调出方会计组织代码
     */
    private String srcFsclUnitCode;

    /**
     * 调出方会计组织名称
     */
    private String srcFsclUnitCp;

    /**
     * 发货仓库编号
     */
    private String delivWarehNum;

    /**
     * 发货仓库名称
     */
    private String delivWarehCp;

    /**
     * 调入方编号
     */
    private String destUnitNum;

    /**
     * 调入方名称
     */
    private String destUnitCp;

    /**
     * 调入方会计组织代码
     */
    private String destFsclUnitCode;

    /**
     * 调入方会计组织名称
     */
    private String destFsclUnitCp;

    /**
     * 收货仓库编号
     */
    private String rcvWarehNum;

    /**
     * 收货仓库名称
     */
    private String rcvWarehCp;

    /**
     * 原始单据组织代码
     */
    private String srcDocUnitCode;

    /**
     * 原始单据组织名称
     */
    private String srcDocUnitCp;

    /**
     * 操作员编号
     */
    private String oprNum;

    /**
     * 操作员姓名
     */
    private String oprCp;

    /**
     * 审核人编号
     */
    private String chkrNum;

    /**
     * 审核人姓名
     */
    private String chkrCp;

    /**
     * 搜索条件 开始日期
     */
    private String startTime;
    /**
     * 搜索条件结束日期
     */
    private String endTime;

    /**
     * 新增时 添加明细
     */
    private List<RgoDtlVo> list;

    public RgoVo() {
    }

    public RgoVo(Long unitId, String rgoNum) {
        super.setUnitId(unitId);
        super.setRgoNum(rgoNum);
    }

    public Long getsUnitId() {
        return sUnitId;
    }

    public void setsUnitId(Long sUnitId) {
        this.sUnitId = sUnitId;
    }

    public String getRgoTypeCp() {
        return rgoTypeCp;
    }

    public void setRgoTypeCp(String rgoTypeCp) {
        this.rgoTypeCp = rgoTypeCp;
    }

    public String getSrcUnitInvdCp() {
        return srcUnitInvdCp;
    }

    public void setSrcUnitInvdCp(String srcUnitInvdCp) {
        this.srcUnitInvdCp = srcUnitInvdCp;
    }

    public String getPrcAutoGenCp() {
        return prcAutoGenCp;
    }

    public void setPrcAutoGenCp(String prcAutoGenCp) {
        this.prcAutoGenCp = prcAutoGenCp;
    }

    public String getPrcAutoChkCp() {
        return prcAutoChkCp;
    }

    public void setPrcAutoChkCp(String prcAutoChkCp) {
        this.prcAutoChkCp = prcAutoChkCp;
    }

    public String getDestUnitInvdCp() {
        return destUnitInvdCp;
    }

    public void setDestUnitInvdCp(String destUnitInvdCp) {
        this.destUnitInvdCp = destUnitInvdCp;
    }

    public String getPucAutoGenCp() {
        return pucAutoGenCp;
    }

    public void setPucAutoGenCp(String pucAutoGenCp) {
        this.pucAutoGenCp = pucAutoGenCp;
    }

    public String getPucAutoChkCp() {
        return pucAutoChkCp;
    }

    public void setPucAutoChkCp(String pucAutoChkCp) {
        this.pucAutoChkCp = pucAutoChkCp;
    }

    public String getDrDiffJgdCp() {
        return drDiffJgdCp;
    }

    public void setDrDiffJgdCp(String drDiffJgdCp) {
        this.drDiffJgdCp = drDiffJgdCp;
    }

    public String getBxiEnabledCp() {
        return bxiEnabledCp;
    }

    public void setBxiEnabledCp(String bxiEnabledCp) {
        this.bxiEnabledCp = bxiEnabledCp;
    }

    public String getSrcDocTypeCp() {
        return srcDocTypeCp;
    }

    public void setSrcDocTypeCp(String srcDocTypeCp) {
        this.srcDocTypeCp = srcDocTypeCp;
    }

    public String getEffectiveCp() {
        return effectiveCp;
    }

    public void setEffectiveCp(String effectiveCp) {
        this.effectiveCp = effectiveCp;
    }

    public String getProgressCp() {
        return progressCp;
    }

    public void setProgressCp(String progressCp) {
        this.progressCp = progressCp;
    }

    public String getCancelledCp() {
        return cancelledCp;
    }

    public void setCancelledCp(String cancelledCp) {
        this.cancelledCp = cancelledCp;
    }

    public String getSuspendedCp() {
        return suspendedCp;
    }

    public void setSuspendedCp(String suspendedCp) {
        this.suspendedCp = suspendedCp;
    }

    public String getFsclUnitCode() {
        return fsclUnitCode;
    }

    public void setFsclUnitCode(String fsclUnitCode) {
        this.fsclUnitCode = fsclUnitCode;
    }

    public String getFsclUnitCp() {
        return fsclUnitCp;
    }

    public void setFsclUnitCp(String fsclUnitCp) {
        this.fsclUnitCp = fsclUnitCp;
    }

    public String getTranWarehNum() {
        return tranWarehNum;
    }

    public void setTranWarehNum(String tranWarehNum) {
        this.tranWarehNum = tranWarehNum;
    }

    public String getTranWarehCp() {
        return tranWarehCp;
    }

    public void setTranWarehCp(String tranWarehCp) {
        this.tranWarehCp = tranWarehCp;
    }

    public String getSrcUnitNum() {
        return srcUnitNum;
    }

    public void setSrcUnitNum(String srcUnitNum) {
        this.srcUnitNum = srcUnitNum;
    }

    public String getSrcUnitCp() {
        return srcUnitCp;
    }

    public void setSrcUnitCp(String srcUnitCp) {
        this.srcUnitCp = srcUnitCp;
    }

    public String getSrcFsclUnitCode() {
        return srcFsclUnitCode;
    }

    public void setSrcFsclUnitCode(String srcFsclUnitCode) {
        this.srcFsclUnitCode = srcFsclUnitCode;
    }

    public String getSrcFsclUnitCp() {
        return srcFsclUnitCp;
    }

    public void setSrcFsclUnitCp(String srcFsclUnitCp) {
        this.srcFsclUnitCp = srcFsclUnitCp;
    }

    public String getDelivWarehNum() {
        return delivWarehNum;
    }

    public void setDelivWarehNum(String delivWarehNum) {
        this.delivWarehNum = delivWarehNum;
    }

    public String getDelivWarehCp() {
        return delivWarehCp;
    }

    public void setDelivWarehCp(String delivWarehCp) {
        this.delivWarehCp = delivWarehCp;
    }

    public String getDestUnitNum() {
        return destUnitNum;
    }

    public void setDestUnitNum(String destUnitNum) {
        this.destUnitNum = destUnitNum;
    }

    public String getDestUnitCp() {
        return destUnitCp;
    }

    public void setDestUnitCp(String destUnitCp) {
        this.destUnitCp = destUnitCp;
    }

    public String getDestFsclUnitCode() {
        return destFsclUnitCode;
    }

    public void setDestFsclUnitCode(String destFsclUnitCode) {
        this.destFsclUnitCode = destFsclUnitCode;
    }

    public String getDestFsclUnitCp() {
        return destFsclUnitCp;
    }

    public void setDestFsclUnitCp(String destFsclUnitCp) {
        this.destFsclUnitCp = destFsclUnitCp;
    }

    public String getRcvWarehNum() {
        return rcvWarehNum;
    }

    public void setRcvWarehNum(String rcvWarehNum) {
        this.rcvWarehNum = rcvWarehNum;
    }

    public String getRcvWarehCp() {
        return rcvWarehCp;
    }

    public void setRcvWarehCp(String rcvWarehCp) {
        this.rcvWarehCp = rcvWarehCp;
    }

    public String getSrcDocUnitCode() {
        return srcDocUnitCode;
    }

    public void setSrcDocUnitCode(String srcDocUnitCode) {
        this.srcDocUnitCode = srcDocUnitCode;
    }

    public String getSrcDocUnitCp() {
        return srcDocUnitCp;
    }

    public void setSrcDocUnitCp(String srcDocUnitCp) {
        this.srcDocUnitCp = srcDocUnitCp;
    }

    public String getOprNum() {
        return oprNum;
    }

    public void setOprNum(String oprNum) {
        this.oprNum = oprNum;
    }

    public String getOprCp() {
        return oprCp;
    }

    public void setOprCp(String oprCp) {
        this.oprCp = oprCp;
    }

    public String getChkrNum() {
        return chkrNum;
    }

    public void setChkrNum(String chkrNum) {
        this.chkrNum = chkrNum;
    }

    public String getChkrCp() {
        return chkrCp;
    }

    public void setChkrCp(String chkrCp) {
        this.chkrCp = chkrCp;
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

    public List<RgoDtlVo> getList() {
        return list;
    }

    public void setList(List<RgoDtlVo> list) {
        this.list = list;
    }
}
