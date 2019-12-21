package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysRoleScope;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysRoleScopeMapper;
import com.boyu.erp.platform.usercenter.service.system.RoleScopeSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @program: boyu-platform
 * @description: 角色范围服务
 * @author: clf
 * @create: 2019-05-31 16:01
 */
@Slf4j
@Service
@Transactional
public class RoleScopeSerivceImpl implements RoleScopeSerivce {
    @Autowired
    private SysRoleScopeMapper roleScopeMapper;

    /**
     * 查询是否存在组织角色范围
     */
    @Override
    @Transactional(readOnly = true)
    public int findByRoleUnit(SysRoleScope roleScope) {
        return roleScopeMapper.counts(roleScope);
    }

    /**
     * 查询组织角色范围
     */
    @Override
    @Transactional(readOnly = true)
    public SysRoleScope getUnitRoleScope(SysRoleScope roleScope) {
        return roleScopeMapper.selectRoleScope(roleScope);
    }

    /**
     * 组织角色范围修改
     * 1. 修改角色组织范围后
     */
    @Override
    public int updateUnitRoleScope(SysRoleScope roleScope, SysUser user) {
        roleScope.setUpdateTime(new Date());
        roleScope.setUpdateBy(user.getUserId());
        return roleScopeMapper.updateByPrimaryKeySelective(roleScope);
    }

    /**
     * 查询用户角色范围
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysRoleScope> getUserRoleScope(SysUser roleScope) {
        return roleScopeMapper.selectUserRoleScope(roleScope);
    }
}
