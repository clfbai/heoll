package com.boyu.erp.platform.usercenter.entity.system;

import lombok.Data;

import java.io.Serializable;

/**
 * sys_unit_role
 * @author 
 */
@Data
public class SysUnitRoleKey implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 角色ID
     */
    private String roleId;



}