package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * scan_tbl (扫描台 表)
 *
 * @author
 */
@Data
@NoArgsConstructor
public class ScanTbl implements Serializable {
    /**
     * 扫描台id
     */
    private String scanTblId;

    /**
     * 扫描台名称
     */
    private String scanTblName;

    /**
     * 仓库id
     */
    private Integer warehId;

    /**
     * 台次
     */
    private String scanTblNum;

    /**
     * 位置
     */
    private String location;

    /**
     * 入库用途
     */
    private String rcvEnabled;

    /**
     * 出库用途
     */
    private String delivEnabled;

    /**
     * 日常用途
     */
    private String dailyEnabled;

    /**
     * 描述
     */
    private String description;

    /**
     * 活动
     */
    private String active;

    /**
     * 任务单据类别
     */
    private String taskDocType;

    /**
     * 任务单据组织id
     */
    private Integer taskDocUnitId;

    /**
     * 任务单据编号
     */
    private String taskDocNum;

    private static final long serialVersionUID = 1L;


}