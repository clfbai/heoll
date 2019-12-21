package com.boyu.erp.platform.usercenter.entity.employee;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * employee  员工表
 *
 * @author
 */
@Data
@NoArgsConstructor
public class Employee extends EmpKey implements Serializable {
    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 照片(照片路径)
     */
    private String portrait;

    /**
     * 职位
     */
    private String jobPos;

    /**
     * 岗位
     */
    private String station;

    /**
     * 工作地点
     */
    private String workplace;

    /**
     * 入职日期
     */
    private Date enrlDate;

    /**
     * 离职日期
     */
    private Date dimsDate;

    /**
     * 离职原因
     */
    private String dimsRsn;

    /**
     * 时间表ID
     */
    private String wttId;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 婚姻状态
     */
    private String mrtStatus;

    /**
     * 政治面貌
     */
    private String party;

    /**
     * 民族
     */
    private String folk;

    /**
     * 籍贯
     */
    private String natvPlc;

    /**
     * 户籍地址
     */
    private String regAddr;

    /**
     * 学历
     */
    private String acadDeg;

    /**
     * 学校
     */
    private String school;

    /**
     * 专业
     */
    private String specialty;

    /**
     * 毕业日期
     */
    private Date gradDate;

    /**
     * 职称
     */
    private String techTtl;

    /**
     * 专长
     */
    private String skill;

    /**
     * 开户银行
     */
    private String bank;

    /**
     * 银行账号
     */
    private String bankAcNum;

    /**
     * 员工状态
     */
    private String emplStatus;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 更新时间
     */
    private Date updTime;



    /**
     * 入职日期String格式
     */
    private String enrlDateCp;

    /**
     * 离职日期String格式
     */
    private String dimsDateCp;
    /**
     * 生日String格式
     */
    private String birthdayCp;

    /**
     * 毕业日期String格式
     */
    private String gradDateCp;


    private static final long serialVersionUID = 1L;

}