package com.boyu.erp.platform.usercenter.entity.basic;

import lombok.Data;

import java.io.Serializable;

/**
 * ab_mtr
 * @author 
 */
@Data
public class AbMtrKey implements Serializable {
    private Long unitId;

    private Long mtrUnitId;

    private static final long serialVersionUID = 1L;

    public AbMtrKey() {
    }

    public AbMtrKey(Long unitId, Long mtrUnitId) {
        this.unitId = unitId;
        this.mtrUnitId = mtrUnitId;
    }
}