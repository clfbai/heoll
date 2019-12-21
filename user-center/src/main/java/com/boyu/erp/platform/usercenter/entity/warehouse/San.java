package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * san
 * @author 
 */
@Data
public class San implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 调整单编号
     */
    private String sanNum;

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
     * 库存形态
     */
    private String stkForm;

    /**
     * 调整类别
     */
    private String sadType;

    /**
     * 总数量
     */
    private BigDecimal ttlQty;

    /**
     * 总箱数
     */
    private Integer ttlBox;

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