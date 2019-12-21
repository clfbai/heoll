package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysRoleScope;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 角色范围接口
 * @author: clf
 * @create: 2019-05-31 16:00
 */
public interface RoleScopeSerivce {
    /**
     * 查询是否存在组织角色范围
     */
    int findByRoleUnit(SysRoleScope roleScope);

    /**
     * 查询组织角色范围
     */
    SysRoleScope getUnitRoleScope(SysRoleScope roleScope);

    int updateUnitRoleScope(SysRoleScope roleScope, SysUser sessionSysUser);

    /**
     * 查询用户角色范围
     */
    List<SysRoleScope> getUserRoleScope(SysUser roleScope);

}
