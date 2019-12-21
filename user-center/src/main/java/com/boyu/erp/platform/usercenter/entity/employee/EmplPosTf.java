package com.boyu.erp.platform.usercenter.entity.employee;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * empl_pos_tf 员工调职记录
 *
 * @author
 */
@Data
@NoArgsConstructor
public class EmplPosTf extends EmpKey implements Serializable {
    /**
     * 调职日期
     */
    private Date tfDate;
    /**
     * 调职日期String
     */
    private Date tfDateCp;

    /**
     * 原部门ID
     */
    private Long fromDeptId;

    /**
     * 原职位
     */
    private String orgJobPos;

    /**
     * 新部门ID
     */
    private Long toDeptId;

    /**
     * 新职位
     */
    private String newJobPos;

    /**
     * 调职原因
     */
    private String tfRsn;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 备注
     */
    private String remarks;
    /**
     * 原部门编号
     */
    private String fromDeptNum;
    /**
     * 新部门编号
     */
    private String toDeptNum;
    /**
     * 原部门名称
     */
    private String fromDeptName;
    /**
     * 新部门名称
     */
    private String toDeptName;

    private static final long serialVersionUID = 1L;


}