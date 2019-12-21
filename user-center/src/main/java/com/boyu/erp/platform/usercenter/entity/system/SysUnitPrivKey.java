package com.boyu.erp.platform.usercenter.entity.system;

import lombok.Data;

import java.io.Serializable;

/**
 * sys_unit_priv
 * @author 
 */
@Data
public class SysUnitPrivKey implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 权限ID
     */
    private String privId;

}