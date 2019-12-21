package com.boyu.erp.platform.usercenter.entity.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * sys_ug_dtl
 *
 * @author
 */
@Data
@NoArgsConstructor
public class SysUgDtl implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 成员ID
     */
    private Long mbrId;

    /**
     * 分组ID
     */
    private Long ugId;

    /**
     * 成员代码
     */
    private String unitCode;

    /**
     * 分组名称
     */
    private String unitName;


}