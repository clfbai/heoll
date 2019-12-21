package com.boyu.erp.platform.usercenter.entity.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * pc_syn_task
 * @author 
 */
@Data
public class PcSynTask implements Serializable {
    /**
     * 单据类别
     */
    private String docType;

    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 单据编号
     */
    private String docNum;

    /**
     * 成本组织ID
     */
    private Long costUnitId;
    /**
     * 挂起
     */
    private String suspended;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;


    public PcSynTask(String docType, Long unitId, String docNum, Long costUnitId, String suspended, String remarks) {
        this.docType = docType;
        this.unitId = unitId;
        this.docNum = docNum;
        this.costUnitId = costUnitId;
        this.suspended = suspended;
        this.remarks = remarks;
    }
}