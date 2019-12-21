package com.boyu.erp.platform.usercenter.entity.collarUse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * arq
 * @author 
 */
@Data
@NoArgsConstructor
public class Arq implements Serializable {

    /**
     * 组织id
     */
    private Long unitId;

    /**
     * 领用单编号
     */
    private String arqNum;
    /**
     * 单据日期
     */
    private Date docDate;

    /**
     * 领用单类别
     */
    private String arqType;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 发货仓库ID
     */
    private Long delivWarehId;

    /**
     * 领用原因
     */
    private String arqRsn;

    /**
     * 货期
     */
    private Date reqdDate;

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
     * 操作员ID
     */
    private Long oprId;

    /**
     * 操作时间
     */
    private Date opTime;

    /**
     * 审核人ID
     */
    private Long chkrId;

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

    private static final long serialVersionUID = 1L;
}