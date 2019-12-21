package com.boyu.erp.platform.usercenter.entity.employee;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * empl_attr 员工属性
 *
 * @author
 */
@Data
@NoArgsConstructor
public class EmplAttr implements Serializable {
    /**
     * 属性值
     */
    private String attrVal;
    /**
     * 员工ID
     */
    private Long emplId;

    /**
     * 属主ID
     */
    private Long ownerId;

    /**
     * 属性类别
     */
    private String attrType;


    private static final long serialVersionUID = 1L;


}