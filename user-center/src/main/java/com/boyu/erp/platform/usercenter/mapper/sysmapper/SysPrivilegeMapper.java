package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.*;

import java.util.List;


public interface SysPrivilegeMapper {

    /**
     * 查询用户权限集合（修改1）
     */
    List<SysPrivilege> getuserPriv(SysUser user);

    /**
     * 查询组织权限集合（修改2）此处只能由管理员进行操作 传入管理员的 User 取属主Id
     */
    List<SysPrivilege> UnitPriv(SysUser user);

    /**
     * 查询权限表权限分页（只对超级管理员操作)
     */
    List<SysPrivilege> getByAllauthoritys(SysUser user);

    /**
     * 查询角色的权限
     *
     * @param role
     * @return
     */
    List<SysPrivilege> selectRolePriv(SysRole role);

    /**
     * 查询权限类别
     */
    List<SysPrivilege> selectPrivType();

    int addAuthority(SysPrivilege pr);

    int updateByPrimaryAuthority(SysPrivilege pr);

    int deletePriv(SysPrivilege pr);

    /**
     * 查询权限范围
     */
    List<SysPrivilege> getTypeScope();


    /**
     * 查询菜单的权限
     *
     * @param menu
     * @return
     */
    List<SysMenuDtl> getMenuTreePriv(SysMenu menu);

    /**
     * 查看权限对应用户
     *
     * @param privilege
     * @return
     */
    List<SysPrsnl> getPrivUser(SysPrivilege privilege);

    /**
     * 查看权限对应组织
     *
     * @param privilege
     * @return
     */
    List<SysUnit> getPrivUnit(SysPrivilege privilege);

    /**
     * 查询操作权限(查询单个权限)
     */
    SysPrivilege OperationAuthorityPriv(String privilegeId);

    /**
     * 功能描述: 查询权限Id是否存在
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/23 10:14
     */
    SysPrivilege selectPrivId(String privId);
}