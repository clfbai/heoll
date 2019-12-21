package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysRole;
import com.boyu.erp.platform.usercenter.entity.system.SysUserRoleKey;
import com.boyu.erp.platform.usercenter.vo.system.UserRoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleMapper {
    /**
     * 用户角色
     */
    public List<SysRole> selectAll(UserRoleVo userRoleVo);

    /**
     * 管理员角色
     */
    public List<SysRole> adminRole(UserRoleVo userRoleVo);

    /**
     * 系统管理员角色
     */
    public List<SysRole> getRole(UserRoleVo userRoleVo);

    public int insert(SysUserRoleKey record);

    public int insertSelective(SysUserRoleKey record);

    public int deleteByPrimaryKey(SysUserRoleKey key);

    int deleteUserRole(SysUserRoleKey key);

    /**
     * 删除角色对应用户
     */
    int deletUserRole(SysRole role);

    /**
     * 批量添加用户角色
     */
    int insertList(@Param("list") List<SysUserRoleKey> list);


    int deleteList(@Param("list") List<SysUserRoleKey>list);
}