package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * psa
 * @author 
 */
@Data
public class Psa implements Serializable {
    /**
     * 供应商ID
     */
    private Long venderId;

    /**
     * 采购商ID
     */
    private Long vendeeId;

    /**
     * 协议控制方("r","e")
     */
    private String psaCtlr;
    /**
     * 供应商协议编号
     */
    private String vdrPsaNum;

    /**
     * 采购商协议编号
     */
    private String vdePsaNum;

    /**
     * 生效日期
     */
    private Date effDate;

    /**
     * 失效日期
     */
    private Date expdDate;

    /**
     * 采购价格参照者ID
     */
    private Long puPrlRefId;

    /**
     * 销售价格参照者ID
     */
    private Long slPrlRefId;

    /**
     * 缺省折率
     */
    private BigDecimal dfltDiscRate;

    /**
     * 缺省税率
     */
    private BigDecimal dfltTaxRate;

    /**
     * 购销定金比例
     */
    private BigDecimal psDpstRate;

    /**
     * 购销保证金比例
     */
    private BigDecimal psGmRate;

    /**
     * 退货控制("n","q","v")
     */
    private String rtnCtrl;

    /**
     * 缺省代销("t","f")
     */
    private String dfltIsCms;

    /**
     * 缺省可退率
     */
    private BigDecimal dfltRtnaRate;

    /**
     * 退货授信比例
     */
    private BigDecimal rtnAccRate;

    /**
     * 返利兑现比例
     */
    private BigDecimal rwdEncRate;

    /**
     * 中转方ID
     */
    private Long tranUnitId;

    /**
     * 购销合同类别
     */
    private String dfltPscType;

    /**
     * 退货合同类别
     */
    private String dfltRtcType;

    /**
     * 更新时间
     */
    private Date updTime;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 是否介入
     */
    private String inte;

    private static final long serialVersionUID = 1L;

    public Psa() {
    }

    public Psa(Long venderId, Long vendeeId) {
        this.venderId = venderId;
        this.vendeeId = vendeeId;
    }
}