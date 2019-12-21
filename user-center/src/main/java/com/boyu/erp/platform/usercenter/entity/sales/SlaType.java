package com.boyu.erp.platform.usercenter.entity.sales;

import lombok.Data;

import java.io.Serializable;

/**
 * sla_type
 * @author 
 */
@Data
public class SlaType implements Serializable {
    /**
     * 销售申请类别
     */
    private String slaType;

    /**
     * 描述
     */
    private String description;

    /**
     * 购销申请类别
     */
    private String psxType;

    /**
     * 活动
     */
    private String active;

    private static final long serialVersionUID = 1L;
}