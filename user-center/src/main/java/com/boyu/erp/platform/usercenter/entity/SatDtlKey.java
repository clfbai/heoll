package com.boyu.erp.platform.usercenter.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * sat_dtl
 * @author +
 * 配码明细
 */
@Data
public class SatDtlKey implements Serializable {
    /**
     * 配码编号
     */
    private Long satId;

    /**
     * 规格编号
     */
    private Long specId;




}