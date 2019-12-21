package com.boyu.erp.platform.usercenter.vo;

import lombok.Data;

/**
 * @Classname SatDtlVo
 * @Description TODO
 * @Date 2019/5/10 16:52
 * @Created by js
 * 配码明细
 */
@Data
public class SatDtlVo {

    /**
     * 配码编号
     */
    private Long satId;

    /**
     * 规格编号
     */
    private Long specId;

    /**
     * 数量
     */
    private Long qty;


    /**
     * 规格编号
     */
    private String specNum;

    /**
     * 规格名称
     */
    private String specName;



}
