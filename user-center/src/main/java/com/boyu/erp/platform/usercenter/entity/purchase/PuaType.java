package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;

import java.io.Serializable;

/**
 * pua_type
 * @author 
 */
@Data
public class PuaType implements Serializable {
    /**
     * 采购申请类别
     */
    private String puaType;

    /**
     * 描述
     */
    private String description;

    /**
     * 购销申请类别
     */
    private String psxType;

    /**
     * 活动("t","f")
     */
    private String active;

    private static final long serialVersionUID = 1L;


}