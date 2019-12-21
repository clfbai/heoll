package com.boyu.erp.platform.usercenter.service.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehDelivTask;
/**
 * 出库任务外部调用接口
 * @author HHe
 * @date 2019/12/2 16:20
 */
public interface WarehDelivTaskExternalCiteService {
    /**
     * 修改出库任务
     * @param delivTask
     * @return
     * @author HHe
     * @date 2019/12/2 16:20
     */
    int updateWarehDelivTask(WarehDelivTask delivTask);
    /**
     * 更新任务执行次数
     * @param delivTask 任务信息:
     *                  任务单据组织Id；
     *                  任务单据编号；
     *                  任务单据类别；
     *                  组织Id；
     * @return 数据库执行数
     * @author HHe
     * @date 2019/12/3 11:49
     */
    int renewalTaskImplTimes(WarehDelivTask delivTask);
}
