package com.boyu.erp.platform.usercenter.entity.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_unit_pa
 * @author 
 */
@Data
public class SysUnitPa implements Serializable {
    /**
     * 权限分配ID
     */
    private Long unitPaId;

    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 授权回收
     */
    private String grRv;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 权限ID
     */
    private String privId;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 操作时间
     */
    private Date opTime;

    private static final long serialVersionUID = 1L;


}