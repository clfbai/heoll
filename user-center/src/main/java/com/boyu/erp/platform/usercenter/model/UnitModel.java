package com.boyu.erp.platform.usercenter.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @program: boyu-platform
 * @description: 组织数据模型
 * @author: clf
 * @create: 2019-05-31 09:41
 */
@Data
@ToString
@NoArgsConstructor
public class UnitModel {
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 组织代码
     */
    private String unitCode;

    /**
     * 组织名称
     */
    private String unitName;
    /**
     * 组织层级
     */
    private String unitHierarchy;

    /**
     * 组织类型
     */
    private String unitType;

    /**
     * 执照类别
     */
    private String licType;

    /**
     * 执照编号
     */
    private String licNum;

    /**
     * 电话号码
     */
    private String telNum;

    /**
     * 传真号码
     */
    private String faxNum;

    /**
     * 电子邮件地址
     */
    private String emailAddr;

    /**
     * 邮政编码
     */
    private String postcode;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区县
     */
    private String district;

    /**
     * 地址
     */
    private String address;

    /**
     * 网站
     */
    private String website;

    /**
     * 联络人ID
     */
    private String lmId;

    /**
     * 管理组织ID
     */
    private Long ctrlUnitId;

    /**
     * 是否共享
     */
    private String shared;

    /**
     * 可征募
     */
    private String recruitable;

    /**
     * 助记码
     */
    private String inputCode;

    /**
     * 序号
     */
    private String seqNum;

    /**
     * 组织状态
     */
    private String unitStatus;

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
}
