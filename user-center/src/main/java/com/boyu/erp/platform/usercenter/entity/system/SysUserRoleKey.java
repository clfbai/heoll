package com.boyu.erp.platform.usercenter.entity.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * sys_user_role
 *
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserRoleKey implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private long userId;

    /**
     * 属主ID
     */
    private long ownerId;

    /**
     * 全局（T/F表示）
     */
    private String unlimited;

    /**
     * 分组ID
     */
    private Long ugId;

    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 角色ID
     */
    private String roleId;

}