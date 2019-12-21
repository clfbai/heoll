package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.*;
import com.boyu.erp.platform.usercenter.vo.RoleUserVo;

import java.util.List;

public interface SysRoleMapper {

    /**
     * 查询用户所有角色
     */
    public List<SysRole> selectAll(SysUser role);

    /**
     * 查询权限所属所有角色
     */
    public List<SysRole> selectPrivRole(SysPrivilege privilege);

    /**
     * 增加角色权限
     */
    public int addRolePriv(SysRolePrivKey role);

    /**
     * 修改角色
     */
    public int updateByRole(SysRole record);

    /**
     * 增加角色
     */
    public int insertRole(SysRole record);

    /**
     * 删除角色权限
     */
    public int deleteRolePriv(SysRolePrivKey role);

    /**
     * 删除角色
     */
    public int deleteRoleId(SysRole roleId);

    /**
     * 查询角色类型
     */
    public List<SysRole> getRoleAll();

    /**
     * 查询系统管理角色
     */
    public List<SysRole> RoleTableAll(SysUser user);

    /**
     * 查询角色对应组织
     *
     * @param role
     */
    List<SysUnit> getRoleUnit(SysRole role);


    /**
     * 查询角色对应用户
     *
     * @param role
     */
    List<RoleUserVo> getRoleUser(SysRole role);

    /**
     * 功能描述: 查询角色Id是否存在
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/23 10:08
     */
    SysRole selectRolrId(String roleId);
}