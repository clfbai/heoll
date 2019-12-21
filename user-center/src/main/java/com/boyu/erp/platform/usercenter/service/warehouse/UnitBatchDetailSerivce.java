package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.UnitBatchDetail;

/**
 * @program: boyu-platform_text
 * @description: 编号操作明细(类似操作日志只增加不减 ， 使用到一定时间后清除前一年或前几个月由用户设定)
 * @author: clf
 * @create: 2019-07-05 14:48
 */
public interface UnitBatchDetailSerivce {

    int addUnitBatchDetail(UnitBatchDetail unitBatchDetail, SysUser user);


}
