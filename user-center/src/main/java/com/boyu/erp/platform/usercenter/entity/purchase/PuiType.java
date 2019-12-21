package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;

import java.io.Serializable;

/**
 * pui_type
 * @author 
 */
@Data
public class PuiType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 采购意向类别
     */
    private String puiType;

    /**
     * 描述
     */
    private String description;

    /**
     * 购销意向类别
     */
    private String psiType;

    /**
     * 活动("t","f")
     */
    private String active;


}