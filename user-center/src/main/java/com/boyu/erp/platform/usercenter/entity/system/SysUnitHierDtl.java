package com.boyu.erp.platform.usercenter.entity.system;

import java.io.Serializable;

/**
 * sys_unit_hier_dtl
 * @author 
 */
public class SysUnitHierDtl implements Serializable {
    /**
     * 组织层级ID
     */
    private String unitHierId;

    /**
     * 组织ID
     */
    private Long unitId;
    /**
     * 属主ID
     */
    private Long ownerId;

    /**
     * 隶属关系
     */
    private String hierarchy;

    private static final long serialVersionUID = 1L;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
    }
}