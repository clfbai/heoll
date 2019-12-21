package com.boyu.erp.platform.usercenter.vo.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用来封装中文和编号
 */
@Data
@NoArgsConstructor
public class SysUnitPgVO implements Serializable {
    /**
     * 组织id
     */
    private Long unitId;
    /**
     * 组织编号
     */
    private String unitCode;
    /**
     * 组织名字
     */
    private String unitName;
}
