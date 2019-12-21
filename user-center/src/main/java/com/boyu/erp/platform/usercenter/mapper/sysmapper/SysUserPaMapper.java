package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysUserPa;

import java.util.List;

public interface SysUserPaMapper {

    /**
     * 条件查询
     */
    public List<SysUserPa> selectAll(SysUserPa userPa);

    /**
     * 主键查询
     */
    public SysUserPa selectByPrimaryKey(String userPaId);

    /**
     * 添加数据
     */
    public int insertSelective(SysUserPa record);

    /**
     * 修改数据
     */
    public int updateByPrimaryKeySelective(SysUserPa record);

    /**
     * 授权回收
     */
    public int accreditRecycle(SysUserPa userPa);
}