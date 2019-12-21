package com.boyu.erp.platform.usercenter.vo;

import lombok.Data;

/**
 * @Classname SatVo
 * @Description TODO
 * @Date 2019/5/10 16:43
 * @Created by js
 */
@Data
public class SatVo {

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

    /**
     * 规格组名称
     */
    private String specGrpName;



}
