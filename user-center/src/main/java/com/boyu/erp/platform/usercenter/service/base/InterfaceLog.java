package com.boyu.erp.platform.usercenter.service.base;

import com.boyu.erp.platform.usercenter.TPOS.entity.godown.common.ResponseOrder;
import com.boyu.erp.platform.usercenter.TPOS.model.CwmsUrlParamModel;
import com.boyu.erp.platform.usercenter.entity.mq.MessageObject;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;

/**
 * 功能描述: 接口调用、消息请求处理日志接口
 *
 * @param:
 * @return:
 * @auther: CLF
 * @date: 2019/11/11 15:15
 */
public interface InterfaceLog {

    /**
     * 功能描述: 记录与C_WMS接口交互日志
     *
     * @param name              操作描述
     * @param cwmsUrlParamModel 请求CWMS对象
     * @param resp              返回参数
     * @param uuid    唯一Id
     * @return:
     * @auther: CLF
     * @date: 2019/11/11 15:18
     */
    int CWMSLog(CwmsUrlParamModel cwmsUrlParamModel, ResponseOrder resp, String name,String uuid, SysUser user) throws Exception;

    /**
     * 功能描述: 记录未投递成功的消息 (ERP 未将消息投递到消息队列的消息)
     * 可能原因 exchange 错误、  routingKey 错误、 网络错误
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/15 20:27
     */
    int updateNoSendMessage(MessageObject messageObject, String exchange, String routingKey);

    /**
     * 功能描述: 记录未绑定到交换机的的消息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/15 20:27
     */
    int addNoExchangeSendMessage(String UUID);

    /**
     * 功能描述: 记录投递到交换机成功的消息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/15 20:27
     */
    int addSendMessage(MessageObject body, String exchange, String routingKey);
}
