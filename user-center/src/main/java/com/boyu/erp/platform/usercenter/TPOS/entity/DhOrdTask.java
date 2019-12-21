package com.boyu.erp.platform.usercenter.TPOS.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 下发订单任务
 * @author 
 */
@Data
public class DhOrdTask implements Serializable {
    /**
     * 接口类型
     */
    private String handleType;

    /**
     * 订单Id
     */
    private String id;

    /**
     * 进度
     */
    private String progress;

    /**
     * 单据来源
     */
    private int bizType;

    /**
     * 等待
     */
    private String waiting;

    /**
     * 执行次数
     */
    private Integer implTimes;

    /**
     * 入队时间
     */
    private Date regTime;

    /**
     * 操作时间
     */
    private Date opTime;

    /**
     * 参数
     */
    private String params;

    /**
     * 备注
     */
    private String errMsg;
    /**
     * 内容
     */
    private String content;
    /**
     * 执行状态
     */
    private String executeStatus;

    private static final long serialVersionUID = 1L;
}