package com.boyu.erp.platform.usercenter.TPOS.service;

import com.boyu.erp.platform.usercenter.TPOS.entity.DhOrdTask;

/**
 * 下发任务接口
 * @author HHe
 * @date 2019/12/4 20:58
 */
public interface DhOrdTaskService {
    /**
     * 登记下发任务
     * @param dhOrdTask 下发任务
     * @return 数据库操作数
     * @author HHe
     * @date 2019/12/4 21:22
     */
    int registerDhOrdTask(DhOrdTask dhOrdTask);
    /**
     * 修改下发任务
     * @param dhOrdTask 下发任务
     * @return 数据库操作数
     * @author HHe
     * @date 2019/12/5 17:34
     */
    int updateTask(DhOrdTask dhOrdTask);
}
