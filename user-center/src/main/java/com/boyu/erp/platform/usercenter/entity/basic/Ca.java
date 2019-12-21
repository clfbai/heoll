package com.boyu.erp.platform.usercenter.entity.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ca
 * @author 
 */
@Data
public class Ca implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 往来账户ID
     */
    private Long caId;
    /**
     * 往来组织ID
     */
    private Long caUnitId;

    /**
     * 往来账户类别
     */
    private String caType;

    /**
     * 余额方向("d","c","b")
     */
    private String balDir;

    /**
     * 借方余额
     */
    private BigDecimal dbBal;

    /**
     * 贷方余额
     */
    private BigDecimal crBal;

    /**
     * 借方表外余额
     */
    private BigDecimal dbMemoBal;

    /**
     * 贷方表外余额
     */
    private BigDecimal crMemoBal;

    /**
     * 借方余额上限
     */
    private BigDecimal dbMaxBal;

    /**
     * 借方余额下限
     */
    private BigDecimal dbMinBal;

    /**
     * 贷方余额上限
     */
    private BigDecimal crMaxBal;

    /**
     * 贷方余额下限
     */
    private BigDecimal crMinBal;

    /**
     * 借方授信余额
     */
    private BigDecimal dbAccBal;

    /**
     * 贷方授信余额
     */
    private BigDecimal crAccBal;

    /**
     * 借方冻结余额
     */
    private BigDecimal dbFrzBal;

    /**
     * 贷方冻结余额
     */
    private BigDecimal crFrzBal;

    /**
     * 应收余额
     */
    private BigDecimal rvaBal;

    /**
     * 应付余额
     */
    private BigDecimal pyaBal;

    /**
     * 冲抵方式("a","m")
     */
    private String bloMode;

    /**
     * 借方起息余额
     */
    private BigDecimal dbInBal;

    /**
     * 借方日利率
     */
    private BigDecimal dbDailyIr;

    /**
     * 借方利息积数
     */
    private BigDecimal dbInBase;

    /**
     * 贷方起息余额
     */
    private BigDecimal crInBal;

    /**
     * 贷方日利率
     */
    private BigDecimal crDailyIr;

    /**
     * 贷方利息积数
     */
    private BigDecimal crInBase;

    /**
     * 转送方ID
     */
    private Long endUnitId;

    /**
     * 外部账户编号
     */
    private String exAcNum;

    /**
     * 账户状态("a","i","d")
     */
    private String caStatus;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 更新时间
     */
    private Date updTime;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;

    public Ca() {
    }

    public Ca(Long unitId, Long caId, Long caUnitId) {
        this.unitId = unitId;
        this.caId = caId;
        this.caUnitId = caUnitId;
    }

    public Ca(Long unitId, Long caUnitId) {
        this.unitId = unitId;
        this.caUnitId = caUnitId;
    }

    public Ca(Long unitId,  Long caUnitId, Long caId, String caType) {
        this.unitId = unitId;
        this.caId = caId;
        this.caUnitId = caUnitId;
        this.caType = caType;
    }

    public Ca(Long unitId, Long caUnitId, String balDir) {
        this.unitId = unitId;
        this.caUnitId = caUnitId;
        this.balDir = balDir;
    }

    public Ca(Long unitId, Long caUnitId, String caStatus, Long oprId) {
        this.unitId = unitId;
        this.caUnitId = caUnitId;
        this.oprId = oprId;
        this.caStatus = caStatus;
    }

}