package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SystemOperationLog;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SystemOperationLogMapper;
import com.boyu.erp.platform.usercenter.service.system.SystemLogSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: boyu-platform
 * @description: 操作日志实现类
 * @author: clf
 * @create: 2019-06-03 14:46
 */
@Slf4j
@Service
@Transactional
public class SystemLogSerivceImpl implements SystemLogSerivce {
    @Autowired
    private SystemOperationLogMapper systemOperationLogMapper;

    @Override
    public int addSystemLog(SystemOperationLog log) {
        return systemOperationLogMapper.insertLog(log);
    }
}
