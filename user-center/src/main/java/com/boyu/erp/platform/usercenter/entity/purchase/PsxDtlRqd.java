package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * psx_dtl_rqd
 * @author 
 */
@Data
@NoArgsConstructor
public class PsxDtlRqd implements Serializable {
    /**
     * 购销申请号
     */
    private BigDecimal qty;
    /**
     * 商品ID
     */
    private String psxNum;

    /**
     * 货期
     */
    private Long prodId;

    /**
     * 数量
     */
    private Date reqdDate;

    private static final long serialVersionUID = 1L;

}