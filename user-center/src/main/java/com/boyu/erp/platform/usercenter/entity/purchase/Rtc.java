package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * rtc
 * @author 
 */
@Data
public class Rtc implements Serializable {
    /**
     * 退货合同号
     */
    private String rtcNum;

    /**
     * 单据日期
     */
    private Date docDate;

    /**
     * 退货合同类别
     */
    private String rtcType;

    /**
     * 关联购销合同("t","f")
     */
    private String pscReqd;

    /**
     * 购销合同号
     */
    private String pscNum;

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
     * 始发方ID
     */
    private Long stUnitId;

    /**
     * 始发仓库ID
     */
    private Long stWarehId;

    /**
     * 采购商介入("t","f")
     */
    private String vdeInvd;

    /**
     * 退购合同自动生成("t","f")
     */
    private String prcAutoGen;

    /**
     * 退购合同已生成("t","f")
     */
    private String prcGen;

    /**
     * 退购合同自动审核("t","f")
     */
    private String prcAutoChk;

    /**
     * 是否居间退购("t","f")
     */
    private String isPrItmd;

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
     * 转送方ID
     */
    private Long endUnitId;

    /**
     * 转送仓库ID
     */
    private Long endWarehId;

    /**
     * 供应商介入("t","f")
     */
    private String vdrInvd;

    /**
     * 退销合同自动生成("t","f")
     */
    private String srcAutoGen;

    /**
     * 退销合同已生成("t","f")
     */
    private String srcGen;

    /**
     * 退销合同自动审核("t","f")
     */
    private String srcAutoChk;

    /**
     * 是否居间退销("t","f")
     */
    private String isSrItmd;

    /**
     * 中转方ID
     */
    private Long tranUnitId;

    /**
     * 差异裁定方("d","r")
     */
    private String drDiffJgd;

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
     * 定价点("ct","dl","rc")
     */
    private String prcSite;

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
     * 占用可退额度("t","f")
     */
    private String rtnaInvd;

    /**
     * 启用授信("t","f")
     */
    private String accEnabled;

    /**
     * 授信点("ck","dd")
     */
    private String rtnAccSite;

    /**
     * 授信金额
     */
    private BigDecimal accVal;

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
     * 退单类别
     */
    private String rtnType;

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
     * 进度("pg","cn","rk","ek","ck","dg","dd","rg","rd","cl")
     */
    private String progress;

    /**
     * 撤销("t","f")
     */
    private String cancelled;

    /**
     * 执行合同编号
     */
    private String execRtcNum;

    /**
     * 居间合同编号
     */
    private String itmdRtcNum;

    /**
     * 始发合同编号
     */
    private String stRtcNum;

    /**
     * 转送合同编号
     */
    private String endRtcNum;

    /**
     * 基准退货编号
     */
    private String baseRtcNum;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 退购属性1
     */
    private String prcAttr1;

    /**
     * 退购属性2
     */
    private String prcAttr2;

    /**
     * 退购属性3
     */
    private String srcAttr1;

    /**
     * 退购属性4
     */
    private String srcAttr2;

    private static final long serialVersionUID = 1L;

    public Rtc() {
    }

    public Rtc(String srcDocType, Long srcDocUnitId, String srcDocNum) {
        this.srcDocType = srcDocType;
        this.srcDocUnitId = srcDocUnitId;
        this.srcDocNum = srcDocNum;
    }
}