package com.boyu.erp.platform.usercenter.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @program: boyu-platform
 * @description: 角色对应用户数据模型
 * @author: clf
 * @create: 2019-05-28 14:32
 */
@Data
@ToString
@NoArgsConstructor
public class RoleUserVo {
    /**
     * 用户代码
     */
    private String prsnlCode;
    /**
     * 用户名字
     */
    private String fullName;
    /**
     * 用户所属组织
     */
    private String unitName;

}
