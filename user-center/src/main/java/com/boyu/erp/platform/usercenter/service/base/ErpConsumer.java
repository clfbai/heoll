package com.boyu.erp.platform.usercenter.service.base;

import com.boyu.erp.platform.usercenter.entity.mq.common.Receipt;
import com.boyu.erp.platform.usercenter.entity.system.ExceptionRequestCwms;
import com.boyu.erp.platform.usercenter.service.system.ExceptionRequestCwmsService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class ErpConsumer {
    @Autowired
    private ExceptionRequestCwmsService excService;
    /**
     *
     * 功能描述: 监听 电商平台回执
     *
     * @param:
     * @return:
     * @auther: CLF
     * @date: 2019/11/27 19:01
     */
    @RabbitListener(queues = "receipt.bc.de")
    public void BandCReceipt(Receipt msg, Channel channel, Message message) throws IOException {
        try {
            log.info("消费者获取消息： [MsgObj]:" + msg);
            this.affirmMessage(msg);
            //确认收到消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  //  @RabbitListener(queues = "receipt.bb.de")
    public void BandBRreceipt(Receipt msg, Channel channel, Message message) throws IOException {
        try {
            log.info("消费者获取消息： [MsgObj]:" + msg);
            this.affirmMessage(msg);
            //确认收到消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void affirmMessage(Receipt msg) {
        ExceptionRequestCwms cwms = new ExceptionRequestCwms();
        cwms.setUuid(msg.getUuid());
        ExceptionRequestCwms repsExc = excService.selectExcepMessage(cwms);
        if (repsExc != null) {
            if (msg.getCode().equalsIgnoreCase("200")) {
                repsExc.setRate("de");
                repsExc.setStatuss(CommonFainl.TRUE);
            } else {
                repsExc.setRate("ng");
                //异常信息
                repsExc.setExcMagess(msg.getMessage());
                repsExc.setStatuss(CommonFainl.FALSE);
            }
            excService.updateExcepCwms(repsExc);
        }

    }

}
