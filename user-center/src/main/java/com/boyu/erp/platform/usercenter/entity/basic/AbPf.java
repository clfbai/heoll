package com.boyu.erp.platform.usercenter.entity.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * ab_pf
 * @author 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbPf implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 账套类别
     */
    private String abType;

    /**
     * 建账会计年度
     */
    private Long initFsclYear;

    /**
     * 建账会计期间
     */
    private Long initFsclPerd;

    /**
     * 当前会计年度
     */
    private Long curFsclYear;

    /**
     * 当前会计期间
     */
    private Long curFsclPerd;

    /**
     * 成本核算方式
     */
    private String costMode;

    /**
     * 多成本组
     */
    private String multiCostGrp;

    /**
     * 账套状态
     */
    private String abStatus;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 操作时间
     */
    private Date opTime;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;
}