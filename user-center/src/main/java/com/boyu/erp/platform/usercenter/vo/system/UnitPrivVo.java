package com.boyu.erp.platform.usercenter.vo.system;

import lombok.Data;

/**
 * @Classname UnitPrivVo
 * @Description TODO
 * @Date 2019/4/28 10:46
 * @Created by js
 */
@Data
public class UnitPrivVo {
    /**
     * 权限ID
     */
    private String privId;

    /**
     * 描述
     */
    private String description;

    /**
     * 权限范围
     */
    private String privScp;

    /**
     * 权限类别
     */
    private String privType;

    /**
     * 组织ID
     */
    private Long unitId;



}
