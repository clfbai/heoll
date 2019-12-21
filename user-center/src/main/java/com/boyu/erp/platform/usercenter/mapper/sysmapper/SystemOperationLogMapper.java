package com.boyu.erp.platform.usercenter.mapper.sysmapper;

import com.boyu.erp.platform.usercenter.entity.system.SystemOperationLog;

public interface SystemOperationLogMapper {
    int deleteSystemLog(Long id);



    int insertLog(SystemOperationLog record);

    SystemOperationLog selectBySystemLog(Long id);

    int updateBySystemLog(SystemOperationLog record);

}