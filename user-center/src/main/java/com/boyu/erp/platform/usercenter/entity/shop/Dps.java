package com.boyu.erp.platform.usercenter.entity.shop;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * dps 商场
 *
 * @author
 */
@Data
@NoArgsConstructor
public class Dps implements Serializable {
    /**
     * 商场ID
     */
    private Long dpsId;

    /**
     * 属主ID
     */
    private Long ownerId;

    /**
     * 商场名称
     */
    private String dpsName;

    /**
     * 商场级别
     */
    private String dpsLvl;

    /**
     * 面积
     */
    private Float acreage;

    /**
     * 总楼层
     */
    private Float floors;

    /**
     * 同类面积
     */
    private Float lkpAcr;

    /**
     * 竞争者信息收集
     */
    private String cptrGthr;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 更新时间
     */
    private Date updTime;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 竞争者信息收集中文名
     */
    private String cptrGthrCP;
    /**
     * 面积级别中文名
     */
    private String dpsLvlCp;


    private static final long serialVersionUID = 1L;


}