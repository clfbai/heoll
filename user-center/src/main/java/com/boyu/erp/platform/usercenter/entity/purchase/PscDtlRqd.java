package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * psc_dtl_rqd
 * @author 
 */
@Data
public class PscDtlRqd implements Serializable {
    /**
     * 购销合同号
     */
    private String pscNum;

    /**
     * 商品ID
     */
    private Long prodId;

    /**
     * 货期
     */
    private Date reqdDate;
    /**
     * 数量
     */
    private BigDecimal qty;

    private static final long serialVersionUID = 1L;

}