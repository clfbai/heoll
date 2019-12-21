package com.boyu.erp.platform.usercenter.entity.employee;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * empl_chg
 *
 * @author
 */
@Data
@NoArgsConstructor
public class EmpKey implements Serializable {
    /**
     * 员工ID
     */
    private Long emplId;

    /**
     * 属主ID
     */
    private Long ownerId;

    /**
     * 行号
     */
    private Integer lineNum;

    private static final long serialVersionUID = 1L;

}