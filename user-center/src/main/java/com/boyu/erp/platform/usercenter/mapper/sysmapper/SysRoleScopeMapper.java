package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysRole;
import com.boyu.erp.platform.usercenter.entity.system.SysRoleScope;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;

import java.util.List;

public interface SysRoleScopeMapper {
    int deleteByPrimaryKey(SysRoleScope key);


    int insertSelective(SysRoleScope record);

    SysRoleScope selectRoleScope(SysRoleScope key);

    int updateByPrimaryKeySelective(SysRoleScope record);


    int counts(SysRoleScope roleScope);

    /***
     *  删除角色对应角色范围表
     */
    int deletRoleScope(SysRole role);

    /**
     * 查询用户角色范围
     */
    List<SysRoleScope> selectUserRoleScope(SysUser key);

}