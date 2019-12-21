package com.boyu.erp.platform.usercenter.entity.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * sys_role_priv
 * @author
 * 角色权限表pojo
 */
@Data
@NoArgsConstructor
public class SysRolePrivKey implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 权限ID
     */
    private String privId;



}