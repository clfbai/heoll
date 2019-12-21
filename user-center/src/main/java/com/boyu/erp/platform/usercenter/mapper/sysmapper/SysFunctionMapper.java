package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SysFunction;

import java.util.List;

public interface SysFunctionMapper {
    /**
     * 功能删除
     * */
    int deleteByPrimaryKey(String funcId);

    int insertSelective(SysFunction record);

    SysFunction selectByPrimaryKey(String funcId);
    /**
     * 功能修改
     * */
    int updateByPrimaryKeySelective(SysFunction record);
    /**
     * (系统管理员)功能菜单查询
     * */
    List<SysFunction> findAll(SysFunction function);

    /**
     * (组织管理员)功能菜单查询
     * */
    List<SysFunction> findByUnit(SysFunction function);
    /**
     * (普通用户)功能菜单查询
     * */
    List<SysFunction> getUserTree(SysFunction function);
    /**
     * 功能类型下拉列表
     * */
   List<SysFunction> getOption();

}