package com.boyu.erp.platform.usercenter.entity.mq.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Receipt implements Serializable {
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 业务编号
     */
    private String uuid;
    /**
     * 返回code
     */
    private String code;
    /**
     * 返回信息
     */
    private String message;
}
