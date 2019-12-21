package com.boyu.erp.platform.usercenter.entity.brand;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * brand
 *
 * @author 品牌
 */
@Data
public class Brand implements Serializable {
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
    private Long holdUnitId;

    /**
     * 负责人ID
     */
    private Long manId;

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
     * 更新时间
     */
    private Date updTime;

    /**
     * LOGO
     */
    private byte[] logo;
    /**
     * 用户组织
     */
    private Long unitId;
    /**
     * 组织品牌权限参数
     */
    private String unitParam;
    /**
     * 用户品牌权限参数
     */
    private String userParam;

    /**
     * 用户Id
     */
    private Long userId;

}