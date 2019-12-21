package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * pkn
 * @author 
 */
@Data
public class Pkn implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 分拣单编号
     */
    private String pknNum;

    /**
     * 单据日期
     */
    private Date docDate;

    /**
     * 出库单编号
     */
    private String gdnNum;

    /**
     * 分配库存形态
     */
    private String allocStkForm;

    /**
     * 实际库存形态
     */
    private String actStkForm;

    /**
     * 分配总数量
     */
    private BigDecimal ttlAllocQty;

    /**
     * 分配总箱数
     */
    private Integer ttlAllocBox;

    /**
     * 实际总数量
     */
    private BigDecimal ttlActQty;

    /**
     * 实际总箱数
     */
    private Integer ttlActBox;

    /**
     * 分拣员ID
     */
    private Long pickerId;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 操作时间
     */
    private Date opTime;

    /**
     * 进度
     */
    private String progress;

    /**
     * 挂起
     */
    private String suspended;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;
}