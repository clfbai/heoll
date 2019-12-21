package com.boyu.erp.platform.usercenter.entity.employee;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * empl_fam_mem   员工家庭成员
 *
 * @author
 */
@Data
@NoArgsConstructor
public class EmplFamMem extends EmpKey implements Serializable {
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
     * 家庭关系
     */
    private String famComp;

    /**
     * 证件类别
     */
    private String idType;

    /**
     * 证件编号
     */
    private String idNum;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 生日String
     */
    private String birthdayCp;

    /**
     * 工作单位
     */
    private String company;

    /**
     * 电话号码
     */
    private String telNum;

    /**
     * 电码
     */
    private String tlgpNum;

    /**
     * 开户银行
     */
    private String bank;

    /**
     * 银行账号
     */
    private String bankAcNum;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;


}