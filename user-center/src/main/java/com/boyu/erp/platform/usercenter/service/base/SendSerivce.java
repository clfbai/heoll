package com.boyu.erp.platform.usercenter.service.base;

import com.alibaba.fastjson.JSONObject;
import com.boyu.erp.platform.usercenter.entity.mq.MessageObject;
import com.boyu.erp.platform.usercenter.utils.common.InterfaceLogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SendSerivce implements RabbitTemplate.ReturnCallback, RabbitTemplate.ConfirmCallback {
    @Autowired
    private InterfaceLogUtils logUtils;


    private RabbitTemplate rabbitTemplate;

    @Autowired
    private InterfaceLog interfaceLog;

    @Autowired
    public SendSerivce(RabbitTemplate rabbitTemplate) {
        super();
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setMandatory(true);
        this.rabbitTemplate.setReturnCallback(this);
        this.rabbitTemplate.setConfirmCallback(this);
    }

    /**
     * 交换机通过 routingKey 绑定消息失败是回调
     * 1.routingKey 错误
     * 2.绑定到 queue 失败
     * 通过路由键 找不到 消息队列 会回调 但是 ack 为 true
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info(",replyCode: " + replyCode +
                ", replyText:" + replyText +
                ", exchange: " + exchange +
                ", routingKey: " + routingKey);

        JSONObject Jons = JSONObject.parseObject(new String(message.getBody()));
        MessageObject body = JSONObject.toJavaObject(Jons, MessageObject.class);
        log.info("递到队列失败======>");
        log.info("交换机: " + exchange);
        log.info("路由键: " + routingKey);
        //修改信息
        interfaceLog.updateNoSendMessage(body, exchange, routingKey);
    }

    /**
     * 将消息绑定到 exchange
     * 1.exchange值错误  ack 为 false
     * 2. exchange 链接管道 change 失败(网络异常)
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("消息发送确认成功消息id:" + correlationData.getId());
        } else {
            log.info("消息发送确认失败消息Id:" + correlationData.getId());
            interfaceLog.addNoExchangeSendMessage(correlationData.getId());
        }
    }

    public void send(String exchange, String routingKey, MessageObject body) {
        if (logUtils.isSend(body.getUuid())) {
            CorrelationData correlationData = new CorrelationData();
            //将 java Bean  转换为 {"body":"123"}  Json 类型String
            // String jsonMessage = JSON.toJSONString(body);
            //记录消息
            interfaceLog.addSendMessage(body, exchange, routingKey);
            correlationData.setId(body.getUuid());
            log.info("{uuid}:" + body.getUuid());
            this.rabbitTemplate.convertAndSend(exchange, routingKey, body, correlationData);
        } else {
            log.info("等待回执、或不能重复发送");
        }
    }
}
