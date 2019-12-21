package com.boyu.erp.platform.usercenter.utils.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: boyu-platform
 * @description: 表字段权限
 * @author: clf
 * @create: 2019-05-18 14:50
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TableFieldPriv {
    /**
     * 表字段对应实体字段
     */
    private String tableFiled;
    /**
     * 权限Id
     */
    private String privId;
}
