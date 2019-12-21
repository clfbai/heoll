package com.boyu.erp.platform.usercenter.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: boyu-platform
 * @description: 权限依赖返回数据模型
 * @author: clf
 * @create: 2019-05-27 10:35
 */
@Data
@ToString
@NoArgsConstructor
public class PrivDelVo {

    private String privId;

    private String description;
}
