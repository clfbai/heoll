package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * tfn  (调拨单表)
 *
 * @author
 */
@Data
@ToString
@NoArgsConstructor
public class Tfn implements Serializable {
    /**
     * 组织ID
     */
    private long unitId;

    /**
     * 调拨单号
     */
    private String tfnNum;

    /**
     * 单据日期
     */
    private Date docDate;

    /**
     * 会计日期
     */
    private Date fsclDate;

    /**
     * 调拨单类别
     */
    private String tfnType;

    /**
     * 会计组织ID
     */
    private long fsclUnitId;

    /**
     * 发货仓库ID
     */
    private long delivWarehId;

    /**
     * 发货会计组织ID
     */
    private long delivFsclUnitId;

    /**
     * 收货仓库ID
     */
    private long rcvWarehId;

    /**
     * 收货会计组织ID
     */
    private long rcvFsclUnitId;

    /**
     * 差异裁定方
     */
    private String drDiffJgd;

    /**
     * 货期
     */
    private Date reqdDate;

    /**
     * 送货方式
     */
    private String delivMthd;

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
     * 操作员ID
     */
    private long oprId;

    /**
     * 操作时间
     */
    private Date opTime;

    /**
     * 审核人ID
     */
    private long chkrId;

    /**
     * 审核时间
     */
    private Date chkTime;

    /**
     * 已生效
     */
    private String effective;

    /**
     * 进度
     */
    private String progress;

    /**
     * 挂起
     */
    private String suspended;

    /**
     * 撤销
     */
    private String cancelled;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 调拨属性1
     */
    private String tfnAttr1;

    /**
     * 调拨属性2
     */
    private String tfnAttr2;

    /**
     * 组织层级ID
     */
    private String unitHierId;

    /**
     * 发货方ID
     */
    private long delivUnitId;

    /**
     * 收货方ID
     */
    private long rcvUnitId;

    /**
     * 单据日期from 用于模糊查询
     */
    private Date docDateFrom;

    /**
     * 单据日期to 用于模糊查询
     */
    private Date docDateTo;

    /**
     * 收货仓库编号
     */
    private String rcvWarehCode;

    /**
     * 发货仓库编号
     */
    private String delivWarehCode;
}