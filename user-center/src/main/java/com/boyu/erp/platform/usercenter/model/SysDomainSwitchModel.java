package com.boyu.erp.platform.usercenter.model;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: boyu-platform
 * @description: 切换领域数据模型
 * @author: clf
 * @create: 2019-06-04 12:01
 */
@Data
@ToString
@NoArgsConstructor
public class SysDomainSwitchModel extends BaseData {
    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 被授权领域
     */
    private Long domainPresentId;

    /**
     * 授权领域
     */
    private Long domainAccreditId;
    /**
     * 被授权切换领域用户Id
     */
    private Long domainUserId;

    /**
     * 修改被授权领域
     */
    private Long updateDomainPresentId;
    /**
     * 修改被授权切换领域用户Id
     */
    private Long updateDomainUserId;
}
