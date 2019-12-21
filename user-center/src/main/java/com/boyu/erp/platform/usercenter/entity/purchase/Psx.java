package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * psx
 * @author
 * 购销申请
 */
@Data
@NoArgsConstructor
public class Psx implements Serializable {
    /**
     * 购销申请号
     */
    private String psxNum;

    /**
     * 单据日期
     */
    private Date docDate;

    /**
     * 购销申请类别
     */
    private String psxType;

    /**
     * 采购商ID
     */
    private Long vendeeId;

    /**
     * 收货仓库ID
     */
    private Long rcvWarehId;

    /**
     * 转送方ID
     */
    private Long endUnitId;

    /**
     * 转送仓库ID
     */
    private Long endWarehId;

    /**
     * 采购商介入
     */
    private String vdeInvd;

    /**
     * 采购申请自动生成
     */
    private String puaAutoGen;

    /**
     * 采购申请已生成
     */
    private String puaGen;

    /**
     * 采购申请自动审核
     */
    private String puaAutoChk;

    /**
     * 供应商ID
     */
    private Long venderId;

    /**
     * 发货仓库ID
     */
    private Long delivWarehId;

    /**
     * 始发方ID
     */
    private Long stUnitId;

    /**
     * 始发仓库ID
     */
    private Long stWarehId;

    /**
     * 供应商介入
     */
    private String vdrInvd;

    /**
     * 销售申请自动生成
     */
    private String slaAutoGen;

    /**
     * 销售申请已生成
     */
    private String slaGen;

    /**
     * 销售申请自动审核
     */
    private String slaAutoChk;

    /**
     * 货期控制
     */
    private String rqdCtrl;

    /**
     * 货期
     */
    private Date reqdDate;

    /**
     * 商品分类ID
     */
    private String prodCatId;

    /**
     * 订单类别
     */
    private String ordType;

    /**
     * 启用配码
     */
    private String bxiEnabled;

    /**
     * 总数量
     */
    private BigDecimal ttlQty;

    /**
     * 总箱数
     */
    private Long ttlBox;

    /**
     * 总金额
     */
    private BigDecimal ttlVal;

    /**
     * 总市值
     */
    private BigDecimal ttlMkv;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 操作时间
     */
    private Date opTime;

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
     * 始发申请编号
     */
    private String stPsxNum;

    /**
     * 转送申请编号
     */
    private String endPsxNum;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;

}