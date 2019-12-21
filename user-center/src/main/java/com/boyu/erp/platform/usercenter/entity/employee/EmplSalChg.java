package com.boyu.erp.platform.usercenter.entity.employee;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * empl_sal_chg   员工薪资变动
 *
 * @author
 */
@Data
@NoArgsConstructor
public class EmplSalChg extends EmpKey implements Serializable {
    /**
     * 变动日期
     */
    private Date chgDate;
    /**
     * 变动日期String
     */
    private String chgDateCp;

    /**
     * 薪金
     */
    private Float salary;

    /**
     * 职位
     */
    private String jobPos;

    /**
     * 调薪原因
     */
    private String chgRsn;

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