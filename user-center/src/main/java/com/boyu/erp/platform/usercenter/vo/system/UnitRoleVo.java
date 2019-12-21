package com.boyu.erp.platform.usercenter.vo.system;

import lombok.Data;

/**
 * @Classname UnitRoleVo
 * @Description TODO
 * @Date 2019/4/28 10:10
 * @Created by js
 * 组织角色
 */
@Data
public class UnitRoleVo {

    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 角色ID
     */
    private String roleId;


    /**
     * 描述
     */
    private String description;



}
