package com.boyu.erp.platform.usercenter.service.system;

import com.boyu.erp.platform.usercenter.entity.system.ExceptionRequestCwms;
import com.boyu.erp.platform.usercenter.exception.ServiceException;

public interface ExceptionRequestCwmsService {

    int addExceptionRequestCwms(ExceptionRequestCwms exceptionRequestCwms);

    ExceptionRequestCwms selectKey(ExceptionRequestCwms exceptionRequestCwms);

    /**
     * 功能描述: 判断记录是否存在C-WMS，存在根据返回参数修改
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/11 17:51
     */
    int updateExcepCwms(ExceptionRequestCwms exceptionRequestCwms) throws ServiceException;

    /**
     * 删除消息记录
     */
    int deleteMessage(ExceptionRequestCwms excMessgae) throws ServiceException;

    /**
     * 增加消息记录
     */
    int addMessage(ExceptionRequestCwms excMessgae) throws ServiceException;

    /**
     * 修改消息
     */
    int updateMessage(ExceptionRequestCwms excMessgae) throws ServiceException;
    /**
     *
     * 功能描述: 根据UUID查询消息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/15 20:45
     */
    ExceptionRequestCwms selectExcepMessage(ExceptionRequestCwms excMessgae);
}
