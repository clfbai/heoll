package com.boyu.erp.platform.usercenter.entity.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_user_pa
 * @author 
 */
@Data
public class SysUserPa implements Serializable {
    /**
     * 权限分配ID
     */
    private long userPaId;

    /**
     * 用户ID
     */
    private long userId;

    /**
     * 属主ID
     */
    private long ownerId;

    /**
     * 授权回收（原本G/R表示）
     */
    private String grRv;

    /**
     * 全局（原本T/F表示）
     */
    private String unlimited;

    /**
     * 分组ID
     */
    private Long ugId;

    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 权限ID
     */
    private String privId;

    /**
     * 操作员ID
     */
    private Long oprId;

    /**
     * 操作时间
     */
    private Date opTime;

    private static final long serialVersionUID = 1L;


}