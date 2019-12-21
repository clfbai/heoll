package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.*;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.PrivModel;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SysPrivilegeSerivce {
    /**
     * 查询用户权限集合(修改第一个）
     */
    List<SysPrivilege> getAuthoritys(SysUser sysUser);

    /**
     * 用户权限分页 (修改2)
     */
    PageInfo<SysPrivilege> getRrivAll(Integer page, Integer size, SysUser user);

    /**
     * 是否是系统管理员、组织管理员、普通用户权限 (修改3)
     */
     List<SysPrivilege> getYnAdmin(SysUser user);

    /**
     * 增加权限
     */
    void addAuthority(SysPrivilege record, SysUser user) throws ServiceException;

    /**
     * 修改权限
     */
    int updateByPrimaryAuthority(SysUser user, List<SysPrivilege> record);


    /**
     * 查询权限列表
     */
    List<SysPrivilege> getByAllauthoritys(SysUser user);

    /**
     * 删除权限
     */
    public void deletePriv(SysPrivilege privilege, SysUser user);

    /**
     * 权限类别查询
     */
    List<SysPrivilege> getAllType();

    /**
     * 权限范围查询
     */
    List<SysPrivilege> getAllTypeScope();


    /**
     * 查询角色的权限
     *
     * @param role
     * @return
     */
    public List<SysPrivilege> getRolePriv(SysRole role);

    /**
     * 权限角色修改
     *
     * @param privilege
     * @return
     */
    public void updatePrivRole(PrivModel privilege);

    /**
     * 查看是具有修改权限表权限
     *
     * @param user
     * @return
     */
    public boolean selectPriv(SysUser user);

    /**
     * 查看权限对应用户
     *
     * @param privilege
     * @return
     */
    List<SysPrsnl> getPrivuser(SysPrivilege privilege);

    /**
     * 查看权限对应组织
     *
     * @param privilege
     * @return
     */
    List<SysUnit> getPrivUnit(SysPrivilege privilege);


    /**
     * 查询操作权限(根据权限Id查询单个权限)
     */
    SysPrivilege OperationAuthorityPriv(String privilegeId);

    /**
     * 增加单条权限(仅增加权限表 此操仅对应操作权限增加)
     */
    int addOperationAuthority(SysPrivilege pr);
}
