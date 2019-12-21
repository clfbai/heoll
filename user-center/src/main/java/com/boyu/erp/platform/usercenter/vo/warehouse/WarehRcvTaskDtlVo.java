package com.boyu.erp.platform.usercenter.vo.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class WarehRcvTaskDtlVo implements Serializable {
    /**
     * 任务单据日期
     */
    private Date taskDocDate;
    /**
     * 任务单据日期 String 类型
     */
    private String taskDocDateCp;
    /**
     * 仓库ID
     */
    private Long warehId;

    /**
     * 入库方式
     */
    private String rcvMode;

    /**
     * 发货方ID
     */
    private Long delivUnitId;

    /**
     * 发货仓库ID
     */
    private Long delivWarehId;

    /**
     * 总数量
     */
    private Float ttlQty;

    /**
     * 总金额
     */
    private Float ttlVal;

    /**
     * 多次执行
     */
    private String multiImpl;

    /**
     * 执行次数
     */
    private Long implTimes;

    /**
     * 入队时间
     */
    private String joinTime;

    /**
     * 挂起
     */
    private String suspended;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 任务单据类别
     */
    private String taskDocType;

    /**
     * 任务单据组织ID
     */
    private Long taskDocUnitId;

    /**
     * 任务单据编号
     */
    private String taskDocNum;


    /**
     * 任务单据类别 中文名称
     */
    private String taskDocTypeCp;
    /**
     * 仓库编号
     */
    private String waerhNum;
    /**
     * 仓库名称
     */
    private String waerhName;

    /**
     * 入队时间 String类型
     */
    private String joinTimeCp;
    /**
     * 发货方编号
     */
    private String delivUnitCode;
    /**
     * 发货方名称
     */
    private String delivUnitName;

    /**
     * 发货仓库编号
     */
    private String delivWarehCode;

    /**
     * 发货仓库名称
     */
    private String delivWarehName;
    /**
     * 入库方式 中文名称
     */
    private String rcvModeCp;

    /**
     * 多次执行 中文名称
     */
    private String multiImplCp;

    /**
     * 挂起 中文名称
     */
    private String suspendedCp;
    /**
     * 开始时间
     */
    private String bengTime;
    /**
     * 结束时间
     */
    private String endTime;
}
