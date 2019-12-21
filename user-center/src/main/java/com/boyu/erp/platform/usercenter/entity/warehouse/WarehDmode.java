package com.boyu.erp.platform.usercenter.entity.warehouse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * wareh_dmode
 * @author 
 */
@Data
@NoArgsConstructor
public class WarehDmode implements Serializable {
    /**
     * 仓库ID
     */
    private Long warehId;
    /**
     * 出库方式
     */
    private String delivMode;
    /**
     * 自动执行
     */
    private String autoExec;

    /**
     * 启用分拣
     */
    private String pickReqd;

    /**
     * 启用复核
     */
    private String rckReqd;

    /**
     * 启用装箱
     */
    private String boxReqd;

    /**
     * 预定装箱
     */
    private String boxSchd;

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
     * 须指定收货方
     */
    private String rcvUnitReqd;

    /**
     * 须指定收货仓库
     */
    private String rcvWarehReqd;

    private static final long serialVersionUID = 1L;
}