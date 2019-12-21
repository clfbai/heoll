package com.boyu.erp.platform.usercenter.entity.system;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * sys_priv_dep
 *
 * @author
 */
@Data
@ToString
@NoArgsConstructor
public class SysPrivDepKey implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    private String privId;

    /**
     * 依赖权限ID
     */
    private String depPrivId;


}