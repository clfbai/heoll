package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysUnitPa;

import java.util.List;

public interface SysUnitPaMapper {

    /**
     * 分页查询
     */
    public List<SysUnitPa> selectAll(SysUnitPa SysUnitPa);

    /**
     * 查询
     */
    public SysUnitPa selectByPrimaryKey(long unitPaId);

    /**
     * 添加
     */
    public int insertSelective(SysUnitPa record);

    /**
     * 修改
     */
    public int updateByPrimaryKeySelective(SysUnitPa record);

    /**
     * 授权回收
     */
    public int accreditRecycle(SysUnitPa SysUnitPa);

}