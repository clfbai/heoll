package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * psc
 * @author 
 */
@Data
public class Psc implements Serializable {
    /**
     * 购销合同号
     */
    private String pscNum;

    /**
     * 单据日期
     */
    private Date docDate;

    /**
     * 购销合同类别
     */
    private String pscType;

    /**
     * 采购商ID
     */
    private Long vendeeId;

    /**
     * 采购商会计组织ID
     */
    private Long vdeFsclUnitId;

    /**
     * 采购商仓库ID
     */
    private Long vdeWarehId;

    /**
     * 转送方ID
     */
    private Long endUnitId;

    /**
     * 转送仓库ID
     */
    private Long endWarehId;

    /**
     * 采购商介入("t","f")
     */
    private String vdeInvd;

    /**
     * 采购合同自动生成("t","f")
     */
    private String pucAutoGen;

    /**
     * 采购合同已生成("t","f")
     */
    private String pucGen;

    /**
     * 采购合同自动审核("t","f")
     */
    private String pucAutoChk;

    /**
     * 是否居间采购("t","f")
     */
    private String isPuItmd;

    /**
     * 供应商ID
     */
    private Long venderId;

    /**
     * 供应商会计组织ID
     */
    private Long vdrFsclUnitId;

    /**
     * 供应商仓库ID
     */
    private Long vdrWarehId;

    /**
     * 始发方ID
     */
    private Long stUnitId;

    /**
     * 始发仓库ID
     */
    private Long stWarehId;

    /**
     * 供应商介入("t","f")
     */
    private String vdrInvd;

    /**
     * 销售合同自动生成("t","f")
     */
    private String slcAutoGen;

    /**
     * 销售合同已生成("t","f")
     */
    private String slcGen;

    /**
     * 销售合同自动审核("t","f")
     */
    private String slcAutoChk;

    /**
     * 是否居间销售("t","f")
     */
    private String isSlItmd;

    /**
     * 中转方ID
     */
    private Long tranUnitId;

    /**
     * 差异裁定方("d","r")
     */
    private String drDiffJgd;

    /**
     * 按指令执行("t","f")
     */
    private String implByIns;

    /**
     * 多次执行("t","f")
     */
    private String multiImpl;

    /**
     * 发货中任务数
     */
    private Long tasksInDeliv;

    /**
     * 收货中任务数
     */
    private Long tasksInRcv;

    /**
     * 是否现货("t","f")
     */
    private String isSpot;

    /**
     * 定价点("ct","dl","rc")
     */
    private String prcSite;

    /**
     * 货期控制("sg","pd","SalesOrder")
     */
    private String rqdCtrl;

    /**
     * 货期
     */
    private Date reqdDate;

    /**
     * 允许增补商品("t","f")
     */
    private String splEnabled;

    /**
     * 超额许可比例
     */
    private BigDecimal exblRate;

    /**
     * 是否代销("t","f")
     */
    private String isCms;

    /**
     * 结算方式("dp","pa","pp")
     */
    private String psStlMthd;

    /**
     * 启用冻款("t","f")
     */
    private String mfzEnabled;

    /**
     * 冻款点("ck","ad")
     */
    private String psMfzSite;

    /**
     * 定金
     */
    private BigDecimal deposit;

    /**
     * 启用保证金("t","f")
     */
    private String gmEnabled;

    /**
     * 保证金
     */
    private BigDecimal guaMon;

    /**
     * 计划控制("t","f")
     */
    private String planCtrl;

    /**
     * 展会编号
     */
    private String tfrNum;

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
     * 商品分类ID
     */
    private String prodCatId;

    /**
     * 订单类别
     */
    private String ordType;

    /**
     * 失效日期
     */
    private Date expdDate;

    /**
     * 送货方式
     */
    private String delivMthd;

    /**
     * 邮政编码
     */
    private String delivPstd;

    /**
     * 送货地址
     */
    private String delivAddr;

    /**
     * 启用配码("t","f")
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
     * 总税款
     */
    private BigDecimal ttlTax;

    /**
     * 总市值
     */
    private BigDecimal ttlMkv;

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
     * 退销总数量
     */
    private BigDecimal ttlRsQty;

    /**
     * 退销总箱数
     */
    private Long ttlRsBox;

    /**
     * 退销总金额
     */
    private BigDecimal ttlRsVal;

    /**
     * 退销总税款
     */
    private BigDecimal ttlRsTax;

    /**
     * 退销总市值
     */
    private BigDecimal ttlRsMkv;

    /**
     * 退购总数量
     */
    private BigDecimal ttlRpQty;

    /**
     * 退购总箱数
     */
    private Long ttlRpBox;

    /**
     * 退购总金额
     */
    private BigDecimal ttlRpVal;

    /**
     * 退购总税款
     */
    private BigDecimal ttlRpTax;

    /**
     * 退购总市值
     */
    private BigDecimal ttlRpMkv;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 操作时间
     */
    private Date opTime;

    /**
     * 已生效("t","f")
     */
    private String effective;

    /**
     * 进度
     */
    private String progress;

    /**
     * 撤销("t","f")
     */
    private String cancelled;

    /**
     * 已递延("t","f")
     */
    private String renewed;

    /**
     * 执行合同编号
     */
    private String execPscNum;

    /**
     * 居间合同编号
     */
    private String itmdPscNum;

    /**
     * 始发合同编号
     */
    private String stPscNum;

    /**
     * 转送合同编号
     */
    private String endPscNum;

    /**
     * 基准购销编号
     */
    private String basePscNum;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 预期总返利
     */
    private BigDecimal ttlExpdRwd;

    /**
     * 发货总返利
     */
    private BigDecimal ttlDelivRwd;

    /**
     * 到货总返利
     */
    private BigDecimal ttlRcvRwd;

    /**
     * 采购属性1
     */
    private String pucAttr1;

    /**
     * 采购属性2
     */
    private String pucAttr2;

    /**
     * 销售属性1
     */
    private String slcAttr1;

    /**
     * 销售属性2
     */
    private String slcAttr2;

    private static final long serialVersionUID = 1L;

    public Psc() {
    }

    public Psc(String srcDocType, Long srcDocUnitId, String srcDocNum) {
        this.srcDocType = srcDocType;
        this.srcDocUnitId = srcDocUnitId;
        this.srcDocNum = srcDocNum;
    }
}