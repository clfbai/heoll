package com.boyu.erp.platform.usercenter.vo.system;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class SysPrsnlVo implements Serializable {


    /**
     * 组织id
     */
    private Long unitId;


    /**
     * 人员ID
     */
    private Long prsnlId;

    /**
     * 人员代码
     */
    private String prsnlCode;

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
     * 管理组织名称
     */
    private String unitName;
    /**
     * 管理组织代码
     */
    private String unitCode;

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
    private long ctrlUnitId;

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
    private long oprId;

    /**
     * 更新时间
     */
    private Date updTime;

    /**
     * 备注
     */
    private String remarks;


    /**
     * 密码过期日期
     */
    private Date upExpdDate;

    /**
     * 密码错误次数
     */
    private String upFltTms;

    /**
     * 密码重激活时间
     */
    private Date upActTime;

    /**
     * IP地址
     */
    private String ipAddr;

    /**
     * 机器控制
     */
    private String machCtrl;

    /**
     * 目录ID
     */
    private String menuId;

    /**
     * 权限日期公式 (原系统没有用到)
     */
    private String privDateFml;

    /**
     * 身份标识
     */
    private String idStr;

    /**
     * 用户类别
     */
    private String userType;

    /**
     * 用户状态
     */
    private String userStatus;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 属主ID
     */
    private Long ownerId;

    private String queryDelete = "false";


    /**
     * 人员组织上级组织
     */
    private Long shuunitId;


    /**
     * 人员用户身份
     */
    private String isType;

}
