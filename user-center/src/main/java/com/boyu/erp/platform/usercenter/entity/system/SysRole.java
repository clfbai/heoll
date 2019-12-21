package com.boyu.erp.platform.usercenter.entity.system;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * sys_role
 *
 * @author 角色表pojo
 */
@Data
@ToString
@NoArgsConstructor
public class SysRole extends SysRolePrivKey implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 描述
     */
    private String description;

    /**
     * 角色类别
     */
    private String roleType;


    /**
     * 修改Id(只接收前端修改角色ID)
     */
    private String roleUpdateId;


    /**
     * 角色范围类型
     */
    private String roleScope;

    /**
     * 角色组织范围
     */
    private String roleBelongUnit;

    /**
     * 角色权限
     */
    private List<SysPrivilege> privileges;

    /**
     * 角色范围(仅支持前台传入)
     */
    private SysRoleScope sysRoleScope;

}