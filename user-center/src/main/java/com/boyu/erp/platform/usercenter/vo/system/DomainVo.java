package com.boyu.erp.platform.usercenter.vo.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class DomainVo implements Serializable {
    /**
     * 组织ID
     */
    private Long unitId;
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 组织名称
     */
    private String unitName;

    /**
     * 组织代码
     */
    private String unitCode;
    /**
     * 组织层级
     */
    private String unitHierarchy;

    /**
     * 组织状态
     */
    private String unitStatus;
}
