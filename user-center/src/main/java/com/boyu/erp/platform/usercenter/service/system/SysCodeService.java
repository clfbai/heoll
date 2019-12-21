package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysCode;
import com.github.pagehelper.PageInfo;

/**
 * @Classname SysCodeService
 * @Description 系统编码服务
 * @Date 2019/5/7 17:51
 * @Created by js
 */
public interface SysCodeService {

    PageInfo<SysCode> selectAll(Integer page, Integer size, SysCode code);

    SysCode getSysCode(SysCode sysCode);

    int insertSysCode(SysCode record);

    int updateSysCode(SysCode record);

    int deleteByPrimaryKey(SysCode sysCode);
}
