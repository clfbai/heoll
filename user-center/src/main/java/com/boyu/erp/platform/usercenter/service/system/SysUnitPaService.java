package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysUnitPa;
import com.github.pagehelper.PageInfo;


public interface SysUnitPaService {


    /**
     * 添加
     */
    public int insertSelective(SysUnitPa record);

    /**
     * 查询
     */
    public SysUnitPa selectByPrimaryKey(long unitPaId);

    /**
     * 修改
     */
    public int updateByPrimaryKeySelective(SysUnitPa record);

    /**
     * 分页查询
     */
    public PageInfo<SysUnitPa> selectAll(Integer page, Integer size, SysUnitPa sysUnitPa);

}
