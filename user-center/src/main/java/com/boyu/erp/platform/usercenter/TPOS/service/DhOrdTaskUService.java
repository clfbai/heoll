package com.boyu.erp.platform.usercenter.TPOS.service;

import com.boyu.erp.platform.usercenter.TPOS.entity.DhOrdTaskU;
/**
 * 上传单据任务接口
 * @author HHe
 * @date 2019/11/8 15:54
 */
public interface DhOrdTaskUService {
    /**
     * 登记上传单据任务接口
     * @param dhOrdTaskU 上传单据任务对象
     * @return 数据库执行数
     * @author HHe
     * @date 2019/11/8 15:56
     */
    void registerDhOrdTaskU(DhOrdTaskU dhOrdTaskU);
    /**
     * 修改上传任务状态和信息
     * @param dhOrdTaskU 状态、异常信息
     * @author HHe
     * @date 2019/12/4 14:39
     */
    void updateDhOrdTaskU(DhOrdTaskU dhOrdTaskU);
}
