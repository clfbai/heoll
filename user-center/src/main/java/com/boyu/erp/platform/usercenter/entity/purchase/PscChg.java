package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * psc_chg
 * @author 
 */
@Data
public class PscChg implements Serializable {
    /**
     * 购销合同号
     */
    private String pscNum;

    /**
     * 记录号
     */
    private Long rcdNum;
    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 操作时间
     */
    private Date opTime;

    private static final long serialVersionUID = 1L;

}