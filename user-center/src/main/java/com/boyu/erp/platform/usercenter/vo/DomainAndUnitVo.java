package com.boyu.erp.platform.usercenter.vo;

import lombok.Data;

import java.util.Date;

@Data
public class DomainAndUnitVo {


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
     * 联络人代码
     */

    private String lmCode;



    /**
     * 联络人姓名
     */

    private String lmName;


    /**
     * 管理组织ID
     */

    private Long ctrlUnitId;
    /**
     * 管理组织代码
     */

    private String ctrlUnitCode;
    /**
     * 管理组织名称
     */

    private String ctrlUnitName;

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
     * 备注
     */

    private String remarks;


    //领域

    /**
     * 领域ID
     */
    private String domainId;

    /**
     * 管理员ID
     */
    private Long saId;

    /**
     * 管理员代码
     */
    private String prsnlCode;

    /**
     * 管理员姓名
     */
    private String fullName;

    /**
     * 权限日期公式(原系统此字段都为空)
     */
    private String privDateFml;

    /**
     * 领域状态
     */
    private String domainStatus;



}
