package com.boyu.erp.platform.usercenter.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Classname BrandVo
 * @Description TODO
 * @Date 2019/5/7 9:35
 * @Created by js
 */
@Data
public class BrandVo {

    /**
     * 品牌ID
     */
    private Long brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 描述
     */
    private String description;

    /**
     * 持有组织ID
     */
    private Integer holdUnitId;

    /**
     * 持有组织代码
     */
    private String holdUnitCode;

    /**
     * 持有组织名称
     */
    private String holdUnitName;

    /**
     * 负责人ID
     */
    private Long manId;

    /**
     * 负责人代码
     */
    private String manCode;

    /**
     * 负责人姓名
     */
    private String manName;

    /**
     * 序号
     */
    private Long seqNum;

    /**
     * 品牌状态
     */
    private String brandStatus;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 操作员代码
     */
    private String oprCode;

    /**
     * 操作员姓名
     */
    private String oprName;

    /**
     * 更新时间
     */
    private Date updTime;

    /**
     * LOGO
     */
    private byte[] logo;

}
