package com.boyu.erp.platform.usercenter.entity.mq;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageObject implements Serializable {
    public MessageObject() {
    }

    public MessageObject(String methodName, String uuid, Object content) {
        this.methodName = methodName;
        this.uuid = uuid;
        this.content = content;
    }

    public MessageObject(String methodName, Object content) {
        this.methodName = methodName;
        this.content = content;
    }

    /**
     * 方法名
     */
    private String methodName;
    /**
     * 业务为一键
     */
    private String uuid;
    /**
     * 消息体
     */
    private Object content;
    /**
     * 业务描述
     */
    private String requestMessage;


}
