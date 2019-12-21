package com.boyu.erp.platform.usercenter.entity.sales;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * spn_scp
 * @author 
 */
@Data
public class SpnScp implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 价格单编号
     */
    private String spnNum;

    /**
     * 采购商ID
     */
    private Long vendeeId;

    /**
     * 供应商ID
     */
    private Long venderId;

    private static final long serialVersionUID = 1L;

    public SpnScp() {
    }

    public SpnScp(Long unitId, String spnNum, Long vendeeId, Long venderId) {
        this.unitId = unitId;
        this.spnNum = spnNum;
        this.vendeeId = vendeeId;
        this.venderId = venderId;
    }
}