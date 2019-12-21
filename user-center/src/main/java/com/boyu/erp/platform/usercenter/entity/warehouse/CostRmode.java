package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;

import java.io.Serializable;

/**
 * cost_rmode
 * @author 
 */
@Data
public class CostRmode implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 入库方式
     */
    private String rcvMode;

    private static final long serialVersionUID = 1L;


}