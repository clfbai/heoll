package com.boyu.erp.platform.usercenter.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: boyu-platform
 * @description: 切换领域session状态对象
 * @author: clf
 * @create: 2019-06-06 09:05
 */
@Data
@ToString
@NoArgsConstructor
public class DomainSwitchSession {
    /**
     * 是否切换领域
     */
    private String isDomainSwitch;
    /**
     * 切换领域用户Id
     */
    private Long userId;
    /**
     * 切换领域用户组织Id
     */
    private Long unitId;
}
