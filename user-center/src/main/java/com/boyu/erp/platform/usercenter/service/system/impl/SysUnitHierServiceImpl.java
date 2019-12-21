package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysUnitHier;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitHierMapper;
import com.boyu.erp.platform.usercenter.service.system.SysUnitHierService;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysUnitHierServiceImpl implements SysUnitHierService {
    @Autowired
    private SysUnitHierMapper sysUnitHierMapper;

    @Override
    public List<SysUnitHier> getSysUnitHierList(SysUnitHier vo) throws ServiceException {
        return sysUnitHierMapper.getSysUnitHierList(vo);
    }

    @Override
    public List<PurKeyValue> optionGet() {
        return sysUnitHierMapper.optionGet();
    }

}
