package com.boyu.erp.platform.usercenter.entity.dept;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * department 部门表
 *
 * @author
 */
@Data
@NoArgsConstructor
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 属主ID
     */
    private Long ownerId;

    /**
     * 上级部门ID
     */
    private Long supDeptId;

    /**
     * 部门级别
     */
    private Integer deptLvl;

    /**
     * 部门职责
     */
    private String deptBiz;

    /**
     * 负责人ID
     */
    private Long manId;

    /**
     * 部门状态
     */
    private String deptStatus;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 更新时间
     */
    private Date updTime;


}