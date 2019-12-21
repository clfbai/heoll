package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysFunction;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.github.pagehelper.PageInfo;

public interface SysFunctionSerivce {
    /**
    * (系统管理员)功能菜单查询
    * */
    PageInfo<SysFunction> findAll(Integer page, Integer size, SysFunction function, SysUser user);

    /**
     * 添加功能
     * */
    int addFunction(SysFunction function);

    /**
     * 修改功能
     * */
    int updateFunction(SysFunction function);
    /**
     * 删除功能
     * */
    public void deleteFunction(SysFunction function);
}
