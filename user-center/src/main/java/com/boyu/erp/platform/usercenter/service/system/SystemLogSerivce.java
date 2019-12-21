package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SystemOperationLog;

/**
 * @program: boyu-platform
 * @description: 操作日志记录接口
 * @author: clf
 * @create: 2019-06-03 14:44
 */
public interface SystemLogSerivce {
    int addSystemLog(SystemOperationLog log);
}
