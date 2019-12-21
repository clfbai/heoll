package com.boyu.erp.platform.usercenter.entity.shop;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * pay_mode  付款方式
 *
 * @author
 */
@Data
@NoArgsConstructor
public class PayMode implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 付款方式ID
     */
    private Integer payModeId;

    /**
     * 付款方式代码
     */
    private String payModeCode;

    /**
     * 付款方式名称
     */
    private String payModeName;

    /**
     * 超额许可
     */
    private String exPmtd;

    /**
     * 是否找零
     */
    private String giveChg;

    /**
     * 是否定额
     */
    private String fixed;

    /**
     * 已删除
     */
    private String deleted;


}