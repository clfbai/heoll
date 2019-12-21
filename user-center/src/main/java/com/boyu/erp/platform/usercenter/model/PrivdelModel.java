package com.boyu.erp.platform.usercenter.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: boyu-platform
 * @description: 权限依赖前端传入数据模型
 * @author: clf
 * @create: 2019-05-27 11:13
 */
@Data
@NoArgsConstructor
public class PrivdelModel {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    private String privId;

    /**
     * 依赖权限ID
     */
    private String depPrivId;

    /**
     * 修改权限依赖ID
     */
    private String updateDepPrivId;
}
