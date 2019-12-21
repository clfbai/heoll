package com.boyu.erp.platform.usercenter.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: boyu-platform
 * @description: 切换领域登陆模型
 * @author: clf
 * @create: 2019-06-03 16:58
 */
@Data
@ToString
@NoArgsConstructor
public class CutDomainModel {
    /**
     * 切换组织Id
     */
    private Long unitId;
}
