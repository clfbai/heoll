package com.boyu.erp.platform.usercenter.model.system;

import lombok.Data;

import java.util.Date;

@Data
public class OwnerPrsnlModel {
    /**
     * 属主Id
     */
    private Long ownerId;
    /**
     * 人员编号
     */
    private String prsnlNum;
    /**
     * 人员ID
     */
    private Long prsnlId;

    /**
     * 人员代码
     */
    private String prsnlCode;
    /**
     * 人员所在组织层级
     */
    private String unitHierarchy;
    /**
     * 姓氏
     */
    private String surname;

    /**
     * 名字
     */
    private String givenName;

    /**
     * 全名
     */
    private String fullName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 证件类别
     */
    private String idType;

    /**
     * 证件编号
     */
    private String idNum;

    /**
     * 办公电话号码
     */
    private String officeNum;

    /**
     * 移动电话号码
     */
    private String mobileNum;

    /**
     * 家庭电话号码
     */
    private String homeNum;

    /**
     * 传真号码
     */
    private String faxNum;

    /**
     * 电码
     */
    private String tlgpNum;

    /**
     * 电子邮件地址
     */
    private String emailAddr;

    /**
     * 即时工具ID
     */
    private String imId;

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
     * 管理组织ID
     */
    private Long ctrlUnitId;

    /**
     * 是否共享
     */
    private String shared;

    /**
     * 助记码
     */
    private String inputCode;

    /**
     * 序号
     */
    private String seqNum;

    /**
     * 人员状态
     */
    private String prsnlStatus;

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
     * 查询导购
     */
    private String empAll = "T";


    public OwnerPrsnlModel(Long ownerId, Long prsnlId) {
        this.ownerId = ownerId;
        this.prsnlId = prsnlId;
    }

    public OwnerPrsnlModel() {
    }
}
