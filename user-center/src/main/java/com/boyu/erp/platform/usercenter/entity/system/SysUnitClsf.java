package com.boyu.erp.platform.usercenter.entity.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * sys_unit_clsf
 * @author 
 */
@Data
public class SysUnitClsf implements Serializable {


    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 属主ID
     */
    private Long ownerId;

    /**
     * 组织I类别
     */
    private String unitType;

    /**
     * 是否冻结
     */
    private String frzType;

    public SysUnitClsf() {
    }

    public SysUnitClsf(Long unitId, Long ownerId) {
        this.unitId = unitId;
        this.ownerId = ownerId;
    }

    public SysUnitClsf(Long unitId, Long ownerId, String unitType) {
        this.unitId = unitId;
        this.ownerId = ownerId;
        this.unitType = unitType;
    }

    public SysUnitClsf(Long ownerId, String unitType, String frzType) {
        this.ownerId = ownerId;
        this.unitType = unitType;
        this.frzType = frzType;
    }

    public SysUnitClsf(Long unitId, Long ownerId, String unitType, String frzType) {
        this.unitId = unitId;
        this.ownerId = ownerId;
        this.unitType = unitType;
        this.frzType = frzType;
    }
}