package com.boyu.erp.platform.usercenter.entity.system;

import lombok.Data;

import java.io.Serializable;

/**
 * 功能规则
 * @author HHe
 * @date 2019/10/9 11:55
 */
@Data
public class FunRule implements Serializable {
    /**
     * 单据类别
     */
    private String docType;
    /**
     * 进度
     */
    private String progress;
    /**
     * 挂起
     */
    private String suspended;
    /**
     * 撤销
     */
    private String cancelled;
    /**
     * 规则
     * code:status|code:status|...
     */
    private String function;
}
