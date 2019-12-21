package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ppn_scp
 * @author 
 */
@Data
public class PpnScp implements Serializable {
    /**
     * 组织id
     */
    private Long unitId;

    /**
     * 价格单编号
     */
    private String ppnNum;

    /**
     * 供应商id
     */
    private Long venderId;

    private static final long serialVersionUID = 1L;

    public PpnScp(Long unitId, String ppnNum, Long venderId) {
        this.unitId = unitId;
        this.ppnNum = ppnNum;
        this.venderId = venderId;
    }

    public PpnScp() {

    }
}