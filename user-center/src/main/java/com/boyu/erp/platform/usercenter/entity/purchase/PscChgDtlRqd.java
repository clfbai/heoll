package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * psc_chg_dtl_rqd
 * @author 
 */
@Data
public class PscChgDtlRqd implements Serializable {
    /**
     * 购销合同号
     */
    private String pscNum;

    /**
     * 记录号
     */
    private Long rcdNum;

    /**
     * 商品ID
     */
    private Long prodId;

    /**
     * 货期
     */
    private Date reqdDate;
    /**
     * 调整数量
     */
    private BigDecimal adjQty;

    private static final long serialVersionUID = 1L;

}