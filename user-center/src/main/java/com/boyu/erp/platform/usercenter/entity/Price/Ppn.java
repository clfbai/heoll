package com.boyu.erp.platform.usercenter.entity.Price;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * ppn
 * @author 
 */
@Data
@NoArgsConstructor
public class Ppn implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 价格单编号
     */
    private String ppnNum;
    /**
     * 单据日期
     */
    private Date docDate;

    /**
     * 价格类别
     */
    private String xpType;

    /**
     * 定价范围
     */
    private String prcScp;

    /**
     * 保留组织定价
     */
    private String rsvUnit;

    /**
     * 定价原因
     */
    private String prcRsn;

    /**
     * 生效日期
     */
    private Date effDate;

    /**
     * 失效日期
     */
    private Date expdDate;

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
     * 执行人ID
     */
    private Long extrId;

    /**
     * 执行时间
     */
    private Date execTime;

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