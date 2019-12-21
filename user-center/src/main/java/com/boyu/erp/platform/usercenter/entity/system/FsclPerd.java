package com.boyu.erp.platform.usercenter.entity.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * fscl_perd
 * @author 
 */
@Data
public class FsclPerd implements Serializable {

    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 会计年度
     */
    private Long fsclYear;

    /**
     * 会计期间
     */
    private Long fsclPerd;

    /**
     * 开始日期	
     */
    private Date fromDate;

    /**
     * 结束日期
     */
    private Date toDate;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;
}