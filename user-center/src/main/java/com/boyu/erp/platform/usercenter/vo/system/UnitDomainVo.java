package com.boyu.erp.platform.usercenter.vo.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UnitDomainVo implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 属主ID
     */
    private Long ownerId;
    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 组织代码
     */
    private String unitCode;

    /**
     * 组织名称
     */
    private String unitName;

    /**
     * 组织层级
     */
    private String unitHierarchy;

    /**
     * 助记码
     */

    private String inputCode;

    /**
     * 组织状态
     */
    private String unitStatus;

    /**
     * 领域Id(等于unitCode)
     */
    private String domainId;
    /**
     * 领域状态
     */
    private String domainStatus;

    /**
     * 权限Id
     * */
    private String privId;
}
