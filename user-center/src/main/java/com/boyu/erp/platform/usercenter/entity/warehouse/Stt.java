package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * stt
 * @author 
 */
@Data
public class Stt implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 盘点表编号
     */
    private String sttNum;
    /**
     * 单据日期
     */
    private Date docDate;

    /**
     * 会计日期
     */
    private Date fsclDate;

    /**
     * 仓库ID
     */
    private Long warehId;

    /**
     * 会计组织ID
     */
    private Long fsclUnitId;

    /**
     * 货位管理
     */
    private String locAdopted;

    /**
     * 配码库存管理
     */
    private String astAdopted;

    /**
     * 装箱库存管理
     */
    private String bstAdopted;

    /**
     * 盘点存储范围
     */
    private String sttAreaScp;

    /**
     * 盘点商品范围
     */
    private String sttProdScp;

    /**
     * 正式盘点
     */
    private String isFrml;

    /**
     * 快照时间
     */
    private Timestamp snptTime;

    /**
     * 预期总数量
     */
    private BigDecimal ttlExpdQty;

    /**
     * 预期总箱数
     */
    private Integer ttlExpdBox;

    /**
     * 实际总数量
     */
    private BigDecimal ttlActQty;

    /**
     * 实际总箱数
     */
    private Integer ttlActBox;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 操作时间
     */
    private Timestamp opTime;

    /**
     * 审核人ID
     */
    private Long chkrId;

    /**
     * 审核时间
     */
    private Timestamp chkTime;

    /**
     * 记账人ID
     */
    private Long acckId;

    /**
     * 记账时间
     */
    private Timestamp postTime;

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