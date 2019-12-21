package com.boyu.erp.platform.usercenter.entity.employee;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * empl_chg  员工异动记录 表
 *
 * @author
 */
@Data
@NoArgsConstructor
public class EmplChg extends EmpKey implements Serializable {
    /**
     * 变动日期
     */
    private Date chgDate;
    private String chgDateCp;

    /**
     * 变动内容
     */
    private String chgCnt;

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