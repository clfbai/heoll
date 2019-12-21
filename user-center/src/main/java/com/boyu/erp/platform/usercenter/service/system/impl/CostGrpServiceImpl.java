package com.boyu.erp.platform.usercenter.service.system.impl;

import com.boyu.erp.platform.usercenter.entity.system.CostGrp;
import com.boyu.erp.platform.usercenter.mapper.system.CostGrpMapper;
import com.boyu.erp.platform.usercenter.service.system.CostGrpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 成本组服务
 * @author HHe
 * @date 2019/9/27 17:02
 */
@Service
public class CostGrpServiceImpl implements CostGrpService {
    @Autowired
    private CostGrpMapper costGrpMapper;
    /**
     * 根据组织查询成本组
     * @author HHe
     * @date 2019/9/27 17:03
     */
    @Override
    public CostGrp queryCostGrpByUnitId(long unitId) {
        return costGrpMapper.queryCostGrpByUnitId(unitId);
    }
}
