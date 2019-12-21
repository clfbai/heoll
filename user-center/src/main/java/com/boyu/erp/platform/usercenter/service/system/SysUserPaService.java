package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysUserPa;
import com.github.pagehelper.PageInfo;


public interface SysUserPaService {

    /**
     * 添加数据
     */
    public int insertSelective(SysUserPa record);

    /**
     * 主键查询
     */
    public SysUserPa selectByPrimaryKey(String userPaId);

    /**
     * 修改数据
     */
    public int updateByPrimaryKeySelective(SysUserPa record);

    /**
     * 条件分页查询
     */
    public PageInfo<SysUserPa> selectAll(Integer page, Integer size,SysUserPa userPa);

}
