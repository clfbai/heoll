package com.boyu.erp.platform.usercenter.service.caservice;

import com.boyu.erp.platform.usercenter.entity.basic.TranDiffEvt;

/**
 * 类描述:
 *
 * @Description: 收发差异
 * @auther: wz
 * @date: 2019/9/12 10:00
 */
public interface TranDiffEvtService {

    /**
     * 登记收发差异
     * @param tran
     * @return
     */
    int register(TranDiffEvt tran);

    /**
     * 取消登记收发差异
     * @param tran
     * @return
     */
    int deregister(TranDiffEvt tran);
}
