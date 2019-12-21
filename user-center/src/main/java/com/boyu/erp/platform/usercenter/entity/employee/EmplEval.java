package com.boyu.erp.platform.usercenter.entity.employee;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * empl_eval  员工考核
 *
 * @author
 */
@Data
@NoArgsConstructor
public class EmplEval extends EmpKey implements Serializable {
    /**
     * 考核日期
     */
    private Date evalDate;
    /**
     * 考核日期String
     */
    private String evalDateCp;
    /**
     * 考核内容
     */
    private String evalCnt;

    /**
     * 考核人ID
     */
    private Long assrId;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 考核员代码
     */
    private String oprCode;
    /**
     * 考核员名称
     */
    private String oprName;

    private static final long serialVersionUID = 1L;


}