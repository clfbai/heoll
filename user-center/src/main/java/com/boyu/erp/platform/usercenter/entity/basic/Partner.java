package com.boyu.erp.platform.usercenter.entity.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * partner
 * @author 
 */
@Data
@AllArgsConstructor
public class Partner implements Serializable {
    /**
     * 伙伴ID
     */
    private Long ptnrId;

    /**
     * 属主ID
     */
    private Long ownerId;
    /**
     * 信用等级
     */
    private String pcrLvl;

    /**
     * 缺省承运商ID
     */
    private Long dfltFwdrId;

    /**
     * 缺省送货方式
     */
    private String dfltDelivMthd;

    /**
     * 经营状况
     */
    private String manCond;

    /**
     * 伙伴状态("a","i","d")
     */
    private String ptnrStatus;

    private static final long serialVersionUID = 1L;

    public Partner() {
    }

    public Partner(Long ptnrId, Long ownerId) {
        this.ptnrId = ptnrId;
        this.ownerId = ownerId;
    }
}