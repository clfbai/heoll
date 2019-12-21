package com.boyu.erp.platform.usercenter.entity.system;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * sys_user_priv
 * @author 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserPrivKey implements Serializable {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 属主ID
     */
    private Long ownerId;

    /**
     * 全局
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
     * 权限ID
     */
    private String privId;

    private static final long serialVersionUID = 1L;


}