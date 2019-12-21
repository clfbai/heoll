package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatchNum;

import java.util.List;

/**
 * @program: boyu-platform_text
 * @description: 批次序号接口
 * @author: clf
 * @create: 2019-07-05 14:46
 */
public interface UnitBatchNumService {


    /**
     * 生产入库或采购入库(供应商未介入)增加批次序号
     */
    int addUnitBatchNum(UnitBatchNum unitBatchNum);


    int reverseReceive(UnitBatchNum unitBatchNum, List<Long> prodIdList, SysUser user);


}
