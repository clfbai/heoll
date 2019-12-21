package com.boyu.erp.platform.usercenter.entity.employee;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * empl_work_exp  员工履历
 * @author
 */
@Data
@NoArgsConstructor
public class EmplWorkExp extends EmpKey implements Serializable {
    /**
     * 开始日期
     */
    private Date fromDate;

    /**
     * 结束日期
     */
    private Date toDate;
    /**
     * 开始日期String
     */
    private String fromDateCp;

    /**
     * 结束日期String
     */
    private String toDateCp;
    /**
     * 工作单位
     */
    private String company;

    /**
     * 职位
     */
    private String jobPos;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 操作员ID
     */
    private Long oprId;

    private static final long serialVersionUID = 1L;


}