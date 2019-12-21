package com.boyu.erp.platform.usercenter.entity.purchase;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * pso
 * @author 
 */
@Data
@NoArgsConstructor
public class Pso implements Serializable {
    /**
     * 购销单号
     */
    private String psoNum;

    /**
     * 单据日期
     */
    private Date docDate;

    /**
     * 购销合同号
     */
    private String pscNum;

    /**
     * 收货仓库id
     */
    private Integer rcvWarehId;

    /**
     * 发货仓库id
     */
    private Integer delivWarehId;

    /**
     * 购销单性质("t","p")
     */
    private String psoKind;

    /**
     * 采购单已生成("t","f")
     */
    private String puoGen;

    /**
     * 销售单已生成("t","f")
     */
    private String sloGen;

    /**
     * 启用配码("t","f")
     */
    private String bxiEnabled;

    /**
     * 货期
     */
    private Date reqdDate;

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
     * 总数量
     */
    private BigDecimal ttlQty;

    /**
     * 总箱数
     */
    private Integer ttlBox;

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
    private Integer ttlDelivBox;

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
    private Integer ttlRcvBox;

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
     * 操作员id
     */
    private Integer oprId;

    /**
     * 操作时间
     */
    private Date opTime;

    /**
     * 已生效("t","f")
     */
    private String effective;

    /**
     * 进度("pg","cn","rk","ek","ck","dg","dd","rg","rd")
     */
    private String progress;

    /**
     * 撤销("t","f")
     */
    private String cancelled;

    /**
     * 执行单据编号
     */
    private String execPsoNum;

    /**
     * 居间单据编号
     */
    private String itmdPsoNum;

    /**
     * 始发单据编号
     */
    private String stPsoNum;

    /**
     * 转送单据编号
     */
    private String endPsoNum;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;
}