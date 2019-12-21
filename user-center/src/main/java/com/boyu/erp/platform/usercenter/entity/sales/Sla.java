package com.boyu.erp.platform.usercenter.entity.sales;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * sla
 * @author 
 */
@Data
@NoArgsConstructor
public class Sla implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 销售申请号
     */
    private String slaNum;
    /**
     * 购销申请号
     */
    private String psxNum;

    /**
     * 销售申请类别
     */
    private String slaType;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 审核人ID
     */
    private Long chkrId;

    /**
     * 审核时间
     */
    private Date chkTime;

    /**
     * 挂起
     */
    private String suspended;

    private static final long serialVersionUID = 1L;

    public String getPsxNum() {
        return psxNum;
    }

    public void setPsxNum(String psxNum) {
        this.psxNum = psxNum;
    }

    public String getSlaType() {
        return slaType;
    }

    public void setSlaType(String slaType) {
        this.slaType = slaType;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getChkrId() {
        return chkrId;
    }

    public void setChkrId(Long chkrId) {
        this.chkrId = chkrId;
    }

    public Date getChkTime() {
        return chkTime;
    }

    public void setChkTime(Date chkTime) {
        this.chkTime = chkTime;
    }

    public String getSuspended() {
        return suspended;
    }

    public void setSuspended(String suspended) {
        this.suspended = suspended;
    }
}