package com.boyu.erp.platform.usercenter.service.emp;

import com.boyu.erp.platform.usercenter.entity.mq.MessageObject;
import com.boyu.erp.platform.usercenter.entity.shop.ShopEmp;
import com.boyu.erp.platform.usercenter.exception.ServiceException;

/**
 * 类描述: 生成导购消息对象接口
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/10/10 16:05
 */
public interface EmpMsgSerivcer {
    /**
     * 功能描述: 新增店员获得导购消息对象
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/10 16:14
     */
    MessageObject getMsgEmp(ShopEmp empKey) throws ServiceException;

    /**
     * 功能描述: 获得冻结导购消息对象
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/10/10 16:19
     */
    MessageObject getFreezeEmp(ShopEmp empKey);

}
