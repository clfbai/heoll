package com.boyu.erp.platform.usercenter.entity.basic;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ca_tx
 * @author 
 */
@Data
public class CaTx implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 往来账事务ID
     */
    private Long caTxId;
    /**
     * 会计日期
     */
    private Date fsclDate;

    /**
     * 交易时间
     */
    private Date txTime;

    /**
     * 往来账户ID
     */
    private Long caId;

    /**
     * 单据类别
     */
    private String docType;

    /**
     * 单据组织ID
     */
    private Long docUnitId;

    /**
     * 单据编号
     */
    private String docNum;

    /**
     * 交易性质
     */
    private String caTxKind;

    /**
     * 交易类别
     */
    private String caTxType;

    /**
     * 余额形态
     */
    private String caValForm;

    /**
     * 是否表外
     */
    private String isMemo;

    /**
     * 借方金额
     */
    private BigDecimal dbVal;

    /**
     * 贷方金额
     */
    private BigDecimal crVal;

    /**
     * 摘要
     */
    private String remarks;

    private static final long serialVersionUID = 1L;

    public CaTx() {
    }


    public CaTx(Long unitId, Long caId, String docType, Long docUnitId, String docNum, String caValForm, String isMemo, BigDecimal dbVal, BigDecimal crVal) {
        this.unitId = unitId;
        this.caId = caId;
        this.docType = docType;
        this.docUnitId = docUnitId;
        this.docNum = docNum;
        this.caValForm = caValForm;
        this.isMemo = isMemo;
        this.dbVal = dbVal;
        this.crVal = crVal;
    }
}