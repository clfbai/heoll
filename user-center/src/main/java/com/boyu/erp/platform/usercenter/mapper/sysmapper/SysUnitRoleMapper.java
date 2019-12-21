package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysRole;
import com.boyu.erp.platform.usercenter.entity.system.SysUnitRoleKey;
import com.boyu.erp.platform.usercenter.vo.system.UnitRoleVo;

import java.util.List;

public interface SysUnitRoleMapper {

    /**
     * 条件查询
     */
    public List<SysRole> selectAll(UnitRoleVo unitRoleVo);

    /**
     * 添加组织角色
     */
    public int insertSelective(SysUnitRoleKey record);

    /**
     * 删除组织角色
     */
    public int deleteByPrimaryKey(SysUnitRoleKey key);


    /**
     * 查询所有角色
     */
    public List<SysRole> getAll(UnitRoleVo unitRoleVo);
    /**
     * 查询组织角色
     */
    List<SysUnitRoleKey> findByUnitId(Long unitId);
}