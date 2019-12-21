package com.boyu.erp.platform.usercenter.config.ampq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

/**
 * 类描述:  发布定于类型 消息
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/9/7 11:49
 */

public class Fanoutconfig {
    /**
     * 消息名称
     */
    public String FANOUT_QUEUE = "NEW_QUEUE";
    /**
     * 交换机名称
     */
    private String EXCHANG_NAME = "MY_EXCHANG";

    /**
     * 创建消息队列
     */
    @Bean
    public Queue fanoutQueue() {
        return new Queue(FANOUT_QUEUE);
    }

    /**
     * 创建交换机(消息模式为 发布/订阅模式)
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANG_NAME);
    }

    /**
     * 消息队列绑定交换机  (fanoutQueue,fanoutExchange 和上面方法名一致 这个是xml注入Id)
     */
    @Bean
    Binding binding(Queue fanoutQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue).to(fanoutExchange);
    }

}
