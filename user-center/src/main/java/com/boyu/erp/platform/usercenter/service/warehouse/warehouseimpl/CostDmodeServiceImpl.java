package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.warehouse.CostDmode;
import com.boyu.erp.platform.usercenter.mapper.warehouse.CostDmodeMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.CostDmodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 影响成本出库方式
 * @author HHe
 * @date 2019/8/29 16:11
 */
@Slf4j
@Service
@Transactional
public class CostDmodeServiceImpl implements CostDmodeService {
    @Autowired
    private CostDmodeMapper costDmodeMapper;

    @Override
    @Transactional(readOnly = true)
    public CostDmode getCostDmodeByUnitMode(Long unitId, String delivMode) {

        return costDmodeMapper.getCostDmodeByUnitMode(unitId,delivMode);
    }
}
