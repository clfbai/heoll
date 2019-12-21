package com.boyu.erp.platform.usercenter.entity.system;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * sys_privilege
 *
 * @author 权限详情表pojo
 */
@Data
@NoArgsConstructor
public class SysPrivilege implements Serializable {
    private static final long serialVersionUID = 1L;
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
     * 修改后权限Id
     */
    private String privupdateId;

    /**
    * 角色
    * */
   private List<SysRole> roles;


}