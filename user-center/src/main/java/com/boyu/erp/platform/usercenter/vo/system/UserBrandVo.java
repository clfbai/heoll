package com.boyu.erp.platform.usercenter.vo.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: boyu-platform
 * @description: 用户品牌返回数据类
 * @author: clf
 * @create: 2019-05-23 09:28
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserBrandVo {
    private Long userId;
    private Long unitId;
    private String brandName;
    private Long brandId;
}
