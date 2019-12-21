package com.boyu.erp.platform.usercenter.entity.system;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * sys_unit_hier
 * @author 
 */
@Data
@ToString
@NoArgsConstructor
public class SysUnitHier implements Serializable {
    /**
     * 组织层级ID
     */
    private String unitHierId;

    /**
     * 组织层级名称
     */
    private String unitHierName;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否可见
     */
    private String visible;

}