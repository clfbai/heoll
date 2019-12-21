package com.boyu.erp.platform.usercenter.TPOS.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 上传订单任务
 * @author 
 */
@Data
public class DhOrdTaskU implements Serializable {
    /**
     * 执行状态
     */
    private String handleType;

    /**
     * 单据类别
     */
    private String docType;

    /**
     * 单据组织ID
     */
    private Long docUnitId;

    /**
     * 单据编号
     */
    private String docNum;

    /**
     * 内容（请求体）
     */
    private String content;

    /**
     * 登记时间
     */
    private Date regTime;

    /**
     * 操作时间
     */
    private Date opTime;

    /**
     * 执行次数
     */
    private int implTimes;

    /**
     * 参数（请求参数）
     */
    private String params;

    /**
     * 错误信息备注
     */
    private String errMsg;

    /**
     * 执行状态
     */
    private String executeStatus;
    private static final long serialVersionUID = 1L;
}