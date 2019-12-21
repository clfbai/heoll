package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysRole;
import com.boyu.erp.platform.usercenter.entity.system.SysUnitRoleKey;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.system.UnitRoleVo;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface SysUnitRoleService {

    /**
     * 删除组织角色
     */
    public int deleteByPrimaryKey(List<SysUnitRoleKey> key, SysUser user);


    /**
     * 添加组织角色
     */
    public int insertSelective(List<SysUnitRoleKey> record, SysUser user);


    /**
     * 组织角色条件查询(更新)
     */
    public PageInfo<SysRole> getList(Integer page, Integer size, UnitRoleVo key, SysUser user);

    /**
     * 查询组织拥有角色
     */
    List<SysUnitRoleKey> findByUnitRole(Long unitId);

}
