package com.boyu.erp.platform.usercenter.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * sat_dtl
 * @author 
 */
@Data
public class SatDtl extends SatDtlKey implements Serializable {
    /**
     * 数量
     */
    private Long qty;


}