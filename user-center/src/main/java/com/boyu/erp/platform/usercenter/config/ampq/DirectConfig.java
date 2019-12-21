package com.boyu.erp.platform.usercenter.config.ampq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DirectConfig {

    //监听消费
    //receipt.bc.de	receipt.add	B2B处理消息后的回执	B2C	NewErp
    //receipt.bb.de	bb.receipt.add	B2B处理消息后的回执	B2B	NewErp

    /**
     * 消息确认交换机名称
     */
    private String directReceiptBcUpExchange = "exh.receipt.bc.up";

    /**
     * 消息确认队列名称
     */
    private String directReceiptBcUpQueue = "receipt.bc.up";
    /**
     * 消息确认据路由键
     */
    private String directReceiptBcUpRoutingKey = "receipt.erp.up";
    @Bean
    public Queue directReceiptBcUpQueue() {
        return new Queue(directReceiptBcUpQueue, true);
    }
    @Bean
    public DirectExchange directReceiptBcUpExchange() {
        return new DirectExchange(directReceiptBcUpExchange, true, false);
    }
    @Bean
    Binding directReceiptBcUpBinding(Queue directReceiptBcUpQueue, DirectExchange directReceiptBcUpExchange) {
        return BindingBuilder.bind(directReceiptBcUpQueue).to(directReceiptBcUpExchange).with(directReceiptBcUpRoutingKey);
    }


    /**
     * 基础数据交换机名称
     */
    private String directMasterExchange = "exh.master.up";

    /**
     * 基础数据队列名称
     */
    private String directQueueMaster = "master.up";
    /**
     * 基础数据路由键
     */
    private String masterRoutingKey = "master.up";

    /**
     * 创建消息队列
     */
    @Bean
    public Queue directMasterQueue() {
        return new Queue(directQueueMaster, true);
    }

    /**
     * 基础数据交换机(消息模式为 直连交换机)
     */
    @Bean
    public DirectExchange directMasterExchange() {
        return new DirectExchange(directMasterExchange, true, false);
    }

    /**
     * 消息队列绑定交换机  (fanoutQueue,fanoutExchange 和上面方法名一致 这个是xml注入Id)
     */
    @Bean
    Binding directBinding(Queue directMasterQueue, DirectExchange directMasterExchange) {
        return BindingBuilder.bind(directMasterQueue).to(directMasterExchange).with(masterRoutingKey);
    }

    /**
     * 采用Json序列化对象
     */
    @Bean
    public RabbitTemplate bindingRabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    /**
     * 基础数据交换机名称
     */
    private String directOrderUpExchange = "exh.order.bc.up";

    /**
     * 基础数据队列名称
     */
    private String directQueueOrderUp = "order.bc.up";
    /**
     * 基础数据路由键
     */
    private String orderUpRoutingKey = "order.bc.up";

    /**
     * 创建消息队列
     */
    @Bean
    public Queue directOrderUpQueue() {
        return new Queue(directQueueOrderUp);
    }

    /**
     * 基础数据交换机(消息模式为 直连交换机)
     */
    @Bean
    public DirectExchange directOrderUpExchange() {
        return new DirectExchange(directOrderUpExchange);
    }

    /**
     * 消息队列绑定交换机  (fanoutQueue,fanoutExchange 和上面方法名一致 这个是xml注入Id)
     */
    @Bean
    Binding directOrderUpBinding(Queue directOrderUpQueue, DirectExchange directOrderUpExchange) {
        return BindingBuilder.bind(directOrderUpQueue).to(directOrderUpExchange).with(orderUpRoutingKey);
    }
}
