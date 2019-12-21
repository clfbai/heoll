package com.boyu.erp.platform.usercenter.entity.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * sys_unit_owner
 * @author 
 */
@Data
@ToString
@AllArgsConstructor
public class SysUnitOwner  implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 属主ID
     */
    private Long ownerId;
    /**
     * 组织编号
     */
    private String unitNum;

    /**
     * 组织别名
     */
    private String unitAlias;

    /**
     * 已删除("t","f")
     */
    private String deleted;

    public SysUnitOwner() {
    }

    public SysUnitOwner(Long unitId, Long ownerId, String deleted) {
        this.unitId = unitId;
        this.ownerId = ownerId;
        this.deleted = deleted;
    }
}