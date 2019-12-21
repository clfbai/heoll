package com.boyu.erp.platform.usercenter.vo.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class UserRoleVo implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 属主ID
     */
    private Long ownerId;

    /**
     * 全局（T/F表示）
     */
    private String unlimited;

    /**
     * 分组ID
     */
    private String ugId;

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

    /**
     * 角色类别
     */
    private String roleType;


    /**
     * 分组编号
     */
    private String ugNum;
    /**
     * 分组名称
     */
    private String ugName;
    /**
     * 组织代码
     */
    private String unitCode;
    /**
     * 组织名称
     */
    private String unitName;
    /**
     * 是否全局中文名
     */
    private String unlimitedCp;

}
