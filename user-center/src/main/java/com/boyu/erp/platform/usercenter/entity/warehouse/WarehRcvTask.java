package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * wareh_rcv_task : 入库任务表
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/7/15 10:37
 */
@Data
public class WarehRcvTask implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务单据日期
     */
    private Date taskDocDate;

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
     * 是否生成入库单 T F(是否显示)
     */
    private String isStb;
    /**
     * 入库单单据号
     */
    private String grnNum;




    public WarehRcvTask() {
    }

    public WarehRcvTask(Long unitId, String taskDocType, Long taskDocUnitId, String taskDocNum) {
        this.unitId = unitId;
        this.taskDocType = taskDocType;
        this.taskDocUnitId = taskDocUnitId;
        this.taskDocNum = taskDocNum;
    }


}