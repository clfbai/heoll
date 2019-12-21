package com.boyu.erp.platform.usercenter.entity.basic;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * vendee
 * @author 
 */
@Data
public class Vendee implements Serializable {
    /**
     * 采购商ID
     */
    private Long vendeeId;

    /**
     * 属主ID
     */
    private Long ownerId;
    /**
     * 协议控制方
     */
    private String psaCtlr;

    /**
     * 对应销售仓库ID
     */
    private Long cpdSlWarehId;

    /**
     * 对应退销仓库ID
     */
    private Long cpdRsWarehId;

    /**
     * 委托代销仓库ID
     */
    private Long cmsWarehId;

    /**
     * 配货权重
     */
    private Short admWgt;

    /**
     * 采购商状态
     */
    private String vdeStatus;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 更新时间
     */
    private Date updTime;

    private static final long serialVersionUID = 1L;

    public Vendee() {
    }

    public Vendee(Long vendeeId, Long ownerId) {
        this.vendeeId = vendeeId;
        this.ownerId = ownerId;
    }

}