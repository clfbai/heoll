package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * stl
 * @author 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stl implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 清单编号
     */
    private String stlNum;

    /**
     * 单据日期
     */
    private Date docDate;

    /**
     * 仓库ID
     */
    private Long warehId;

    /**
     * 盘点表编号
     */
    private String sttNum;

    /**
     * 货位管理
     */
    private String locAdopted;

    /**
     * 库存形态
     */
    private String stkForm;

    /**
     * 总数量
     */
    private BigDecimal ttlQty;

    /**
     * 总箱数
     */
    private Long ttlBox;

    /**
     * 盘点人ID
     */
    private Long stkrId;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 操作时间
     */
    private Timestamp opTime;

    /**
     * 进度
     */
    private String progress;

    /**
     * 挂起
     */
    private String suspended;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;
}