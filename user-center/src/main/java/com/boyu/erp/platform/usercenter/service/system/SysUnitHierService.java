package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.SysUnitHier;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;

import java.util.List;

public interface SysUnitHierService {

    public List<SysUnitHier> getSysUnitHierList(SysUnitHier vo) throws ServiceException;

    /**
     * 组织层级下拉框
     */
    List<PurKeyValue> optionGet();
}
