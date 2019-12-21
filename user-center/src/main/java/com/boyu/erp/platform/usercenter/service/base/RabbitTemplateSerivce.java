package com.boyu.erp.platform.usercenter.service.base;

import com.boyu.erp.platform.usercenter.entity.mq.MessageObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitTemplateSerivce {
    @Autowired
    private RabbitTemplate rabbitTemplate;


   /* public RabbitTemplateSerivce(RabbitTemplate rabbitTemplate) {
        super();
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setMandatory(true);
        this.rabbitTemplate.setReturnCallback(this);
    }*/

    /**
     * 功能描述: 发送基础数据消息方法
     *
     * @param: queueName(消息名称)
     * @return:
     * @auther: CLF
     * @date: 2019/9/7 12:28
     */
    public void send(MessageObject messageObject) {
        log.info("HelloSender发送内容 : " + messageObject);
        ack();
        this.rabbitTemplate.convertAndSend("exh.master.up", "master.up", messageObject);
    }

    //发送消息确认
    public void ack() {
        this.rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {

        });
        /*this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                log.info("Sender消息发送失败" + cause + correlationData);
            } else {
                log.info("HelloSender 消息发送成功 ");
            }
        });*/
    }

    public void sendByOrder(MessageObject messageObject) {
        log.info("Sender发送内容 : " + messageObject);
        ack();
        this.rabbitTemplate.convertAndSend("exh.order.bc.up", "order.bc.up", messageObject);
    }


    /**
     * exchange将消息发送到 queue失败则回调
     */
    //@Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息体:" + new String(message.getBody()) + ",replyCode:" + replyCode + ",replyText:"
                + replyText + ",exchange:" + exchange + ",routingKey:" + routingKey);
        try {
            Thread.sleep(10000L);
            // TODO 重新发送消息至队列,此处应写一套重发机制,重发多少次结束,否则如果消息如果一直发送失败,则会一直发下去!
            this.rabbitTemplate.convertAndSend(exchange, routingKey, message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
