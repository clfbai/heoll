package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.SysUnitClsf;
import com.boyu.erp.platform.usercenter.mapper.sysmapper.SysUnitClsfMapper;
import com.boyu.erp.platform.usercenter.service.system.SysUnitClsfSerivcer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional

public class SysUnitClsfSerivcerImpl implements SysUnitClsfSerivcer {
    @Autowired
    private SysUnitClsfMapper unitClsfMapper;

    @Override
    public int addUnitClsf(SysUnitClsf unitClsf) {
        return unitClsfMapper.insertSysUnitClsf(unitClsf);
    }

    @Override
    public int updateUnitClsf(SysUnitClsf unitClsf) {
        return 0;
    }
    /**
     * 查询并返回含有组织Id的组织Id
     * @author HHe
     * @date 2019/10/12 9:57
     */
    @Override
    public List<Long> queryHaveInUnitIds(String unitType,Long ownerId,List<Long> unitIds) {
        return unitClsfMapper.queryHaveInUnitIds(unitType,ownerId,unitIds);
    }
}
