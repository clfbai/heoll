package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUgDtlMapper;
import com.boyu.erp.platform.usercenter.service.system.SysUgDtlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 组织分组明细服务
 * @author HHe
 * @date 2019/11/26 11:15
 */
@Slf4j
@Transactional
@Service
public class SysUgDtlServiceImpl implements SysUgDtlService {
    @Autowired
    private SysUgDtlMapper sysUgDtlMapper;
    /**
     * 根据组织分组Id集合查询成员集合
     * @author HHe
     * @date 2019/11/26 11:18
     */
    @Override
    public List<Long> queryMkrIdsByUgIds(List<Long> unitUgIds) {
        return sysUgDtlMapper.queryMkrIdsByUgIds(unitUgIds);
    }
}
