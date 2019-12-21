package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysPrivilege;
import com.boyu.erp.platform.usercenter.entity.system.SysRole;
import com.boyu.erp.platform.usercenter.entity.system.SysUnit;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.RolePrivModel;
import com.boyu.erp.platform.usercenter.vo.RoleUserVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SysRoleService {
    /**
     * 查询角色表角色（系统管理员)
     */
    List<SysRole> AdminAndUser(SysUser user);

    /**
     * 查询用户所有角色分页
     */
    PageInfo<SysRole> selectAll(Integer page, Integer size, SysUser role);

    /**
     * 查询用户所有角色
     */
    List<SysRole> getRoleAll(SysUser role);

    /**
     * 增加角色
     */
    int addRole(SysRole role, SysUser user) throws ServiceException;

    /**
     * 修改角色
     */
    int upadteRole(SysRole role);

    /**
     * 删除角色
     */
    void deleteRole(SysRole role, SysUser user);

    /**
     * 增加角色权限
     */
    int addRolePriv(SysRole role);

    /**
     * 删除角色权限
     */
    int deleteRolePriv(SysRole role);

    /**
     * 查询角色权限分页
     */
    List<SysPrivilege> selectByRoleIdPriv(SysRole role);

    /**
     * 查询权限对应角色
     */
    List<SysRole> selectPrivRoles(SysPrivilege privilege, SysUser user);

    /**
     * 查询角色类别
     */
     List<SysRole> getRoleAll();

    /**
     * 修改角色权限
     */
     void updateRolePriv(RolePrivModel model);


    /**
     * 是否具有修改角色表权限查询
     */
     boolean selectRoleTable(SysUser user);

    /**
     * 查询角色对应组织
     *
     * @param role
     */
    List<SysUnit> getRoleUint(SysRole role);

    /**
     * 查询角色对应用户
     *
     * @param role
     */
    List<RoleUserVo> getRoleUser(SysRole role);
}
