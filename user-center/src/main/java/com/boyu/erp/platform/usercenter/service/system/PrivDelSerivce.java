package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysPrivDepKey;
import com.boyu.erp.platform.usercenter.model.PrivdelModel;

import java.util.List;

/**
 * @program: boyu-platform
 * @description: 权限依赖接口
 * @author: clf
 * @create: 2019-05-27 09:31
 */

public interface PrivDelSerivce {

    /**
     * 通过权限Id 查询权限依赖
     */
    List<SysPrivDepKey> selectPrivDelKey(SysPrivDepKey key);

    /**
     * 通过权限Id和依赖权限Id查询
     */
    SysPrivDepKey findByPrivDel(SysPrivDepKey depKey);

    /**
     * 添加依赖权限
     */
    int addPrivDel(SysPrivDepKey depKey);

    /**
     * 删除依赖权限
     */
    int deletePrivDel(SysPrivDepKey depKey);

    /**
     * 修改依赖权限
     */
    int updatePrivDel(PrivdelModel depKey);
}
