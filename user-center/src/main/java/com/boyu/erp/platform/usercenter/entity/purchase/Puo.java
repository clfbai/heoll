package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * puo
 * @author 
 */
@Data
@NoArgsConstructor
public class Puo implements Serializable {
    /**
     * 公司id
     */
    private Long unitId;

    /**
     * 公司id
     */
    private String puoNum;
    /**
     * 公司id
     */
    private String psoNum;

    /**
     * 公司id
     */
    private Long chkrId;

    /**
     * 公司id
     */
    private Date chkTime;

    /**
     * 公司id("t","f")
     */
    private String suspended;

    private static final long serialVersionUID = 1L;
    
}