package com.boyu.erp.platform.usercenter.entity.sales;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * rgo
 * @author 
 */
@Data
public class Rgo implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 调配单号
     */
    private String rgoNum;
    /**
     * 单据日期
     */
    private String docDate;

    /**
     * 调配单类别
     */
    private String rgoType;

    /**
     * 会计组织ID
     */
    private Long fsclUnitId;

    /**
     * 中转仓库ID
     */
    private Long tranWarehId;

    /**
     * 调出方ID
     */
    private Long srcUnitId;

    /**
     * 调出方会计组织ID
     */
    private Long srcFsclUnitId;

    /**
     * 发货仓库ID
     */
    private Long delivWarehId;

    /**
     * 调出方介入
     */
    private String srcUnitInvd;

    /**
     * 退购合同自动生成
     */
    private String prcAutoGen;

    /**
     * 退购合同自动审核
     */
    private String prcAutoChk;

    /**
     * 调入方ID
     */
    private Long destUnitId;

    /**
     * 调入方会计组织ID
     */
    private Long destFsclUnitId;

    /**
     * 收货仓库ID
     */
    private Long rcvWarehId;

    /**
     * 调入方介入
     */
    private String destUnitInvd;

    /**
     * 采购合同自动生成
     */
    private String pucAutoGen;

    /**
     * 采购合同自动审核
     */
    private String pucAutoChk;

    /**
     * 差异裁定方
     */
    private String drDiffJgd;

    /**
     * 启用配码
     */
    private String bxiEnabled;

    /**
     * 原始单据类别
     */
    private String srcDocType;

    /**
     * 原始单据组织ID
     */
    private Long srcDocUnitId;

    /**
     * 原始单据编号
     */
    private String srcDocNum;

    /**
     * 总数量
     */
    private BigDecimal ttlQty;

    /**
     * 总箱数
     */
    private Long ttlBox;

    /**
     * 调出总金额
     */
    private BigDecimal ttlSrcVal;

    /**
     * 调出总税款
     */
    private BigDecimal ttlSrcTax;

    /**
     * 调出总市值
     */
    private BigDecimal ttlSrcMkv;

    /**
     * 调入总金额
     */
    private BigDecimal ttlDestVal;

    /**
     * 调入总税款
     */
    private BigDecimal ttlDestTax;

    /**
     * 调入总市值
     */
    private BigDecimal ttlDestMkv;

    /**
     * 发货总数量
     */
    private BigDecimal ttlDelivQty;

    /**
     * 发货总箱数
     */
    private Long ttlDelivBox;

    /**
     * 发货总金额
     */
    private BigDecimal ttlDelivVal;

    /**
     * 发货总税款
     */
    private BigDecimal ttlDelivTax;

    /**
     * 发货总市值
     */
    private BigDecimal ttlDelivMkv;

    /**
     * 到货总数量
     */
    private BigDecimal ttlRcvQty;

    /**
     * 到货总箱数
     */
    private Long ttlRcvBox;

    /**
     * 到货总金额
     */
    private BigDecimal ttlRcvVal;

    /**
     * 到货总税款
     */
    private BigDecimal ttlRcvTax;

    /**
     * 到货总市值
     */
    private BigDecimal ttlRcvMkv;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 操作时间
     */
    private String opTime;

    /**
     * 审核人ID
     */
    private Long chkrId;

    /**
     * 审核时间
     */
    private String chkTime;

    /**
     * 已生效
     */
    private String effective;

    /**
     * 进度
     */
    private String progress;

    /**
     * 撤销
     */
    private String cancelled;

    /**
     * 挂起
     */
    private String suspended;

    /**
     * 备注
     */
    private String remarks;

    private String unitHierId;

    private static final long serialVersionUID = 1L;

    public Rgo() {
    }

    public Rgo(Long unitId, String rgoNum) {
        this.unitId = unitId;
        this.rgoNum = rgoNum;
    }
}