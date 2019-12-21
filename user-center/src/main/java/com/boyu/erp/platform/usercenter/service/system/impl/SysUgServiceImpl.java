package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUgMapper;
import com.boyu.erp.platform.usercenter.service.system.SysUgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
/**
 * 组织分组服务
 * @author HHe
 * @date 2019/9/5 9:57
 */
@Service
@Transactional
public class SysUgServiceImpl implements SysUgService {
    @Autowired
    private SysUgMapper sysUgMapper;
    /**
     * 组织分组下拉Map
     * @author HHe
     * @date 2019/9/5 9:57
     */
    @Transactional(readOnly = true)
    @Override
    public List<Map<String, String>> getSysUgMapByTypeOwner(String ugType, Long unitId) {
        return sysUgMapper.getSysUgMapByTypeOwner(ugType,unitId);
    }
}
