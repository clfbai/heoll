package com.boyu.erp.platform.usercenter.entity.sales;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * slo
 * @author 
 */
@Data
@NoArgsConstructor
public class Slo implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 销售单号
     */
    private String sloNum;
    /**
     * 购销单号
     */
    private String psoNum;

    /**
     * 审核人ID
     */
    private Long chkrId;

    /**
     * 审核时间
     */
    private Date chkTime;

    /**
     * 挂起
     */
    private String suspended;

    private static final long serialVersionUID = 1L;

}