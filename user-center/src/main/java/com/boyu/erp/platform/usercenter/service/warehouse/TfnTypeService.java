package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.TfnType;
import com.github.pagehelper.PageInfo;

/**
 * @program: boyu-platform_text
 * @description: 调拨单类别接口
 * @author: clf
 * @create: 2019-06-25 09:49
 */
public interface TfnTypeService {

    PageInfo<TfnType> getAll(TfnType t, Integer page,Integer size) throws Exception;

    int insertTfnType(TfnType t, SysUser u) throws Exception;

    int updateTfnType(TfnType t, SysUser u) throws Exception;

    int deleteTfnType(TfnType t, SysUser u) throws Exception;
}
