package com.boyu.erp.platform.usercenter.entity.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * exception_request_cwms
 *
 * @author
 */
@Data
public class ExceptionRequestCwms implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 请求参数(Json或xml)
     */
    private String requestParam;

    /**
     * 路由键
     */
    private String routingKey;

    /**
     * 请求URL(http请求为URL)
     */
    private String url;

    /**
     * 请求名称
     */
    private String requestMagess;

    /**
     * 异常描述
     */
    private String excMagess;

    /**
     * 交换机
     */
    private String exchange;

    /**
     * 请求时间
     */
    private Date requestData;

    /**
     * 唯一键(接受为消息队列使用)
     */
    private String uuid;

    /**
     * 执行次数
     */
    private Integer impltimes;

    /**
     * T执行成功，F执行失败
     */
    private String statuss;

    /**
     * 是否删除 T 是
     */
    private String isDel;

    /**
     * 进度(sd 发送成功，de 处理成功，ng处理失败,nd 发送失败)
     */
    private String rate;
    /**
     * 是否是消息
     */
    private String isMessage;
}