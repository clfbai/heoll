package com.boyu.erp.platform.usercenter.entity.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * sys_user_qs
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserQsKey implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 功能ID
     */
    private String funcId;



}