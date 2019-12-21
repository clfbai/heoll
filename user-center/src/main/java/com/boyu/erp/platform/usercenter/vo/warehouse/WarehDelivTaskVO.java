package com.boyu.erp.platform.usercenter.vo.warehouse;

import com.boyu.erp.platform.usercenter.entity.warehouse.WarehDelivTask;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class WarehDelivTaskVO extends WarehDelivTask implements Serializable {
    /**
     * 仓库编号
     */
    private String warehNum;
    /**
     * 仓库名称
     */
    private String warehName;
    /**
     * 任务单据类别（中文）
     */
    private String taskDocTypeCp;
    /**
     * 收货方编号
     */
    private String rcvUnitNum;
    /**
     * 收货方名称
     */
    private String rcvUnitName;
    /**
     * 收货方仓库编号
     */
    private String rcvWarehNum;
    /**
     * 收货方仓库名称
     */
    private String rcvWarehName;
    /**
     * 出库方式（中文）
     */
    private String delivModeCp;
    /**
     * 多次执行（中文）
     */
    private String multiImplCp;
    /**
     * 挂起（中文）
     */
    private String suspendedCp;
}

