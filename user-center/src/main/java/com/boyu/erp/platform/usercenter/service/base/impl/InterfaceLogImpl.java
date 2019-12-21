package com.boyu.erp.platform.usercenter.service.base.impl;

import com.alibaba.fastjson.JSONObject;
import com.boyu.erp.platform.usercenter.TPOS.entity.godown.common.ResponseOrder;
import com.boyu.erp.platform.usercenter.TPOS.model.CwmsUrlParamModel;
import com.boyu.erp.platform.usercenter.entity.mq.MessageObject;
import com.boyu.erp.platform.usercenter.entity.system.ExceptionRequestCwms;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.service.base.InterfaceLog;
import com.boyu.erp.platform.usercenter.service.system.ExceptionRequestCwmsService;
import com.boyu.erp.platform.usercenter.utils.common.InterfaceLogUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@Transactional
public class InterfaceLogImpl implements InterfaceLog {
    @Autowired
    private ExceptionRequestCwmsService exceptionRequestCwmsService;

    @Autowired
    private InterfaceLogUtils logUtils;

    /**
     * 记录C-WMS 品台信息
     *
     * @param name              (操作描述)
     * @param resp              (返回对象)
     * @param cwmsUrlParamModel (请求对象)
     */
    @Override
    public int CWMSLog(CwmsUrlParamModel cwmsUrlParamModel, ResponseOrder resp, String name, String uuid, SysUser user) throws Exception {
        ExceptionRequestCwms keys = new ExceptionRequestCwms();
        keys.setUuid(uuid);
        ExceptionRequestCwms excCwms = exceptionRequestCwmsService.selectExcepMessage(keys);
        keys.setUrl("/user/text/send");
        keys.setRequestData(new Date());
        keys.setIsDel(CommonFainl.FALSE);
        keys.setIsMessage(CommonFainl.FALSE);
        if ("200".equals(resp.getCode())||resp.getFlag().equalsIgnoreCase("success")) {
            keys.setStatuss(CommonFainl.TRUE);
            keys.setRate("de");
        } else {
            //执行失败
            keys.setStatuss(CommonFainl.FALSE);
            keys.setRate("ng");
            keys.setExcMagess(resp.getMessage());
        }
        if (excCwms == null) {
            //将java对象转换为json类型的String字符串
            String requestParam = JSONObject.toJSONString(cwmsUrlParamModel);
            //再次请求接口
            keys.setRequestMagess(name);
            keys.setRequestParam(requestParam);
            keys.setImpltimes(1);
            return exceptionRequestCwmsService.addExceptionRequestCwms(keys);
        } else {
            if (excCwms.getRequestMagess().indexOf("重发") < 0) {
                keys.setRequestMagess(name + "重发");
            }
            keys.setImpltimes(excCwms.getImpltimes() + 1);
            return exceptionRequestCwmsService.updateExcepCwms(keys);
        }


    }

    /**
     * 记录w未投递待消息队列的信息
     * 有可能在消息队列绑定 exchange时记录过一次成功的消息
     * 所以在记录本条消息是判断是否 有UUID存在已成功的消息
     * 有则修改状态为未发送
     * 没有则增加该消息
     */
    @Override
    public int updateNoSendMessage(MessageObject messageObject, String exchange, String routingKey) {
        ExceptionRequestCwms selectKey = createMessage(messageObject, exchange, routingKey);
        //查询是否已存在消息
        ExceptionRequestCwms msg = exceptionRequestCwmsService.selectExcepMessage(selectKey);
        if (msg == null) {
            //赋值
            setMessageExceptionRequest(selectKey, false);
            //新增
            selectKey.setRequestData(new Date());
            return exceptionRequestCwmsService.addMessage(selectKey);
        } else {
            setMessageExceptionRequest(msg, false);
            //已存在消息 修改
            return exceptionRequestCwmsService.updateMessage(msg);
        }

    }

    /**
     * 功能描述: 记录未绑定到交换机的的消息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/15 20:27
     */
    @Override
    public int addNoExchangeSendMessage(String UUID) {
        ExceptionRequestCwms selectKey = new ExceptionRequestCwms();
        selectKey.setUuid(UUID);
        //查询是否已存在消息
        ExceptionRequestCwms msg = exceptionRequestCwmsService.selectExcepMessage(selectKey);
        //赋值
        setMessageExceptionRequest(msg, false);
        //新增
        selectKey.setRequestData(new Date());
        return exceptionRequestCwmsService.addMessage(selectKey);
    }

    /**
     * 功能描述: 记录投递到交换机成功的消息
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/15 20:27
     */
    @Override
    public int addSendMessage(MessageObject body, String exchange, String routingKey) {
        //赋值
        ExceptionRequestCwms selectKey = createMessage(body, exchange, routingKey);
        //检查UUID是否存在
        ExceptionRequestCwms msg = exceptionRequestCwmsService.selectExcepMessage(selectKey);
        if (msg == null) {
            setMessageExceptionRequest(selectKey, true);
            //新增
            selectKey.setRequestData(new Date());
            return exceptionRequestCwmsService.addMessage(selectKey);
        } else {
            //重发
            setMessageExceptionRequest(msg, true);
            msg.setRequestData(new Date());
            msg.setRequestMagess(msg.getRequestMagess() + "重发");
            return exceptionRequestCwmsService.updateMessage(msg);
        }

    }


    /**
     * 验证消息必要条件并封装 参数、URL 等主要信息
     */
    public ExceptionRequestCwms createMessage(MessageObject messageObject, String exchange, String routingKey) throws ServiceException {
        logUtils.isVerify(messageObject.getUuid(), exchange, routingKey);
        ExceptionRequestCwms exceCwms = new ExceptionRequestCwms();
        exceCwms.setUuid(messageObject.getUuid());
        exceCwms.setExchange(exchange);
        exceCwms.setRoutingKey(routingKey);
        //业务描述
        exceCwms.setRequestMagess(messageObject.getRequestMessage());
        exceCwms.setRequestParam(JSONObject.toJSONString(messageObject));
        //发送消息接口
        exceCwms.setUrl("/send/message");
        return exceCwms;
    }

    /**
     * 根据发送类型给消息赋值
     */
    public void setMessageExceptionRequest(ExceptionRequestCwms msg, boolean b) {
        msg.setIsMessage(CommonFainl.TRUE);
        msg.setUrl("/send/message");
        if (b) {
            msg.setRate("sd");
            msg.setStatuss(CommonFainl.TRUE);
            msg.setExcMagess("发送到消息队列成功");
        } else {
            msg.setRate("nd");
            msg.setStatuss(CommonFainl.FALSE);
            msg.setExcMagess("发送到消息队列失败");
        }
        msg.setImpltimes(1);
        msg.setIsDel(CommonFainl.FALSE);
    }
}
