package com.boyu.erp.platform.usercenter.TPOS.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 人员表
 * @author HHe
 * @date 2019/11/4 12:29
 */
@Data
public class Worker implements Serializable {
    /**
     * 公司名称
     */
    private String company;
    /**
     * 姓名
     */
    private String name;
    /**
     * 固定电话
     */
    private String tel;
    /**
     * 移动电话
     */
    private String mobile;
    /**
     * 移动电话
     */
    private String id;
    /**
     * 车牌号
     */
    private String carNo;
    /**
     * 邮编
     */
    private String zipCode;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 国家二字码
     */
    private String countryCode;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区域
     */
    private String area;
    /**
     * 村镇
     */
    private String town;
    /**
     * 详细地址
     */
    private String detailAddress;
    /**
     * 客户编码
     */
    private String receiverCode;
}
