package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * wareh_rmode (仓库入库方式)
 *
 * @author
 */
@Data
@ToString
public class WarehRmode implements Serializable {

    public WarehRmode(Long warehId, String rcvMode) {
        this.warehId = warehId;
        this.rcvMode = rcvMode;
    }
    public WarehRmode() {
    }

    /**
     * 仓库id
     */
    private Long warehId;

    /**
     * 入库方式
     */
    private String rcvMode;
    /**
     * 自动执行
     */
    private String autoExec;

    /**
     * 启用装箱
     */
    private String boxReqd;

    /**
     * 预定装箱
     */
    private String boxSchd;

    /**
     * 启用验收
     */
    private String acptReqd;

    /**
     * 启用分储
     */
    private String paReqd;

    /**
     * 监控差异
     */
    private String diffMtrd;

    /**
     * 差异控制
     */
    private String diffCtrl;

    /**
     * 手工返利
     */
    private String mnlRwd;

    /**
     * 即时结算
     */
    private String instStl;

    /**
     * 固定单价
     */
    private String fixedUnitPrice;

    /**
     * 须指定发货方
     */
    private String delivUnitReqd;

    /**
     * 须指定发货仓库
     */
    private String delivWarehReqd;


    /**
     * 入库方式(中文)
     */
    private String rcvModeCp;
    /**
     * 自动执行(中文)
     */
    private String autoExecCp;

    /**
     * 启用装箱(中文)
     */
    private String boxReqdCp;

    /**
     * 预定装箱(中文)
     */
    private String boxSchdCp;

    /**
     * 启用验收(中文)
     */
    private String acptReqdCp;

    /**
     * 启用分储(中文)
     */
    private String paReqdCp;

    /**
     * 监控差异(中文)
     */
    private String diffMtrdCp;

    /**
     * 差异控制(中文)
     */
    private String diffCtrlCp;

    /**
     * 手工返利(中文)
     */
    private String mnlRwdCp;

    /**
     * 即时结算(中文)
     */
    private String instStlCp;

    /**
     * 固定单价(中文)
     */
    private String fixedUnitPriceCp;

    /**
     * 须指定发货方(中文)
     */
    private String delivUnitReqdCp;

    /**
     * 须指定发货仓库(中文)
     */
    private String delivWarehReqdCp;
}