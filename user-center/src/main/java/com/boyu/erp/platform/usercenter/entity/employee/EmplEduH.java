package com.boyu.erp.platform.usercenter.entity.employee;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * empl_edu_h  员工教育经历
 *
 * @author
 */
@Data
@NoArgsConstructor
public class EmplEduH extends EmpKey implements Serializable {
    /**
     * 开始日期
     */
    private Date fromDate;

    /**
     * 结束日期
     */
    private Date toDate;
    /**
     * 开始日期 String
     */
    private String fromDateCp;

    /**
     * 结束日期 String
     */
    private String toDateCp;
    /**
     * 教育机构
     */
    private String eduOrg;

    /**
     * 教育内容
     */
    private String eduCnt;

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