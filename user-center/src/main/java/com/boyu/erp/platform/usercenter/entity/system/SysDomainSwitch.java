package com.boyu.erp.platform.usercenter.entity.system;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * sys_domain_switch
 *
 * @author
 */
@Data
@ToString
@NoArgsConstructor
public class SysDomainSwitch extends BaseData {
    private static final long serialVersionUID = 1L;
    private Long id;

    /**
     * 授权领域Id
     */
    private Long domainPresentId;

    /**
     * 被授权领域
     */
    private Long domainAccreditId;
    /**
     * 被授权用户Id
     */
    private Long domainUserId;

    /**
     * 组织名称（前台传入)
     */
    private String unitName;
    /**
     * 领域ID
     */
    private String domainId;
}