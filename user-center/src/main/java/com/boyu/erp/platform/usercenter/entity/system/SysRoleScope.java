package com.boyu.erp.platform.usercenter.entity.system;

import com.boyu.erp.platform.usercenter.entity.BaseData;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * sys_role_scope
 *
 * @author
 */
@Data
@ToString
@NoArgsConstructor
public class SysRoleScope extends BaseData implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 组织ID
     */
    private Long unitId;

    /**
     * 角色对应组织范围 all 表示当前组织和下属组织， ol 表示当前组织，or 表示多组织
     */
    private String roleScope;

    /**
     * 所属组织
     */
    private String roleBelongUnit;


}