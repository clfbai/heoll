package com.boyu.erp.platform.usercenter.utils.rolescope;

import com.boyu.erp.platform.usercenter.entity.system.SysRoleScope;
import com.boyu.erp.platform.usercenter.service.system.RoleScopeSerivce;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: boyu-platform
 * @description: 角色范围工具类
 * @author: clf
 * @create: 2019-05-31 15:59
 */
public class RoleScopeutils {

    @Autowired
    private static RoleScopeSerivce roleScopeSerivce;


    public static int getRoleUnit(SysRoleScope sysRoleScope) {
        return roleScopeSerivce.findByRoleUnit(sysRoleScope);
    }
}
