package com.boyu.erp.platform.usercenter.service.warehouse.warehouseimpl;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatchDetail;
import com.boyu.erp.platform.usercenter.mapper.warehouse.UnitBatchDetailMapper;
import com.boyu.erp.platform.usercenter.service.warehouse.UnitBatchDetailSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: boyu-platform_text
 * @description: 批次号明细服务
 * @author: clf
 * @create: 2019-07-05 14:55
 */
@Slf4j
@Service
@Transactional
public class UnitBatchDetailSerivceImpl implements UnitBatchDetailSerivce {
    @Autowired
    private UnitBatchDetailMapper unitBatchDetailMapper;

    /**
     * 功能描述: 增加批次号明细
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/7/9 11:47
     */
    @Override
    public int addUnitBatchDetail(UnitBatchDetail unitBatchDetail, SysUser user) {
        return unitBatchDetailMapper.insertBatchDetail(unitBatchDetail);
    }


}
