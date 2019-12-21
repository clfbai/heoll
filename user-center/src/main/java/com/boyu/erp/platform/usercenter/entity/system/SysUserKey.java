package com.boyu.erp.platform.usercenter.entity.system;

import lombok.Data;

import java.io.Serializable;

/**
 * sys_user
 * @author 
 */
@Data
public class SysUserKey implements Serializable {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 属主ID
     */
    private Long ownerId;

    private static final long serialVersionUID = 1L;

}