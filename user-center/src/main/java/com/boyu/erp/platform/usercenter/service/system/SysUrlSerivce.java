package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysUrl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.github.pagehelper.PageInfo;

/**
 * @program: boyu-platform
 * @description: 路径接口
 * @author: clf
 * @create: 2019-05-31 11:41
 */
public interface SysUrlSerivce {
    PageInfo<SysUrl> pageUrl(Integer page, Integer size, SysUrl url);

    int addUrl(SysUrl url, SysUser sessionSysUser);

    int updateUrl(SysUrl url, SysUser nullUser);

    /**
     * 添加或修改查询是否有已存在url
     */
    Boolean findUrl(SysUrl url);

    int deleteUrl(SysUrl url, SysUser nullUser);
}
