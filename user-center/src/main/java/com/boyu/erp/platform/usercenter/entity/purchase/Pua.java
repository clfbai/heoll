package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * pua
 * @author
 * 采购申请
 */
@Data
@NoArgsConstructor
public class Pua implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 采购申请号
     */
    private String puaNum;
    /**
     * 购销申请号
     */
    private String psxNum;

    /**
     * 采购申请类别
     */
    private String puaType;

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

}