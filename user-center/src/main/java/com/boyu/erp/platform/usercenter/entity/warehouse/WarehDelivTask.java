package com.boyu.erp.platform.usercenter.entity.warehouse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * wareh_deliv_task
 * @author
 */
@Data
public class WarehDelivTask implements Serializable {
    /**
     * 组织id
     * NOT NULL
     */
    private Long unitId;

    /**
     * 任务单据类别
     * NOT NULL
     */
    private String taskDocType;

    /**
     * 任务单据组织ID
     * NOT NULL
     */
    private Long taskDocUnitId;

    /**
     * 任务单据编号
     * NOT NULL
     */
    private String taskDocNum;

    /**
     * 任务单据日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date taskDocDate;

    /**
     * 仓库ID
     */
    private Long warehId;

    /**
     * 出库方式
     * NOT NULL
     */
    private String delivMode;

    /**
     * 收货方ID
     */
    private Long rcvUnitId;

    /**
     * 收货仓库ID
     */
    private Long rcvWarehId;

    /**
     * 总数量
     * NOT NULL
     */
    private Float ttlQty;

    /**
     * 总金额
     * NOT NULL
     */
    private Float ttlVal;

    /**
     * 多次执行
     * NOT NULL
     */
    private String multiImpl;

    /**
     * 执行次数
     * NOT NULL
     */
    private Long implTimes;

    /**
     * 入队时间
     * NOT NULL
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp joinTime;

    /**
     * 挂起
     */
    private String suspended;

    /**
     * 备注
     */
    private String remarks;

    /**
     *  出库任务状态（已生成：yg，未生成：ng）
     */
    private String progress;



    private static final long serialVersionUID = 1L;

    public WarehDelivTask() {
    }

    public WarehDelivTask(Long unitId, String taskDocType, Long taskDocUnitId, String taskDocNum) {
        this.unitId = unitId;
        this.taskDocType = taskDocType;
        this.taskDocUnitId = taskDocUnitId;
        this.taskDocNum = taskDocNum;
    }
}