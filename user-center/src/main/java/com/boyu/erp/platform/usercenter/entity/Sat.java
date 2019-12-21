package com.boyu.erp.platform.usercenter.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * sat
 * @author
 * 配码
 */
@Data
public class Sat implements Serializable {
    /**
     * 配码ID
     */
    private Long satId;

    /**
     * 规格组ID
     */
    private String specGrpId;

    /**
     * 配码编号
     */
    private String satNum;

    /**
     * 配码名称
     */
    private String satName;

    /**
     * 特征串
     */
    private String egnStr;

    /**
     * 总数量
     */
    private Long ttlQty;

    /**
     * 序号
     */
    private Long seqNum;

    /**
     * 描述
     */
    private String description;

}