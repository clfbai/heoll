package com.boyu.erp.platform.usercenter.model.system;

import com.boyu.erp.platform.usercenter.entity.system.SysUserRoleKey;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserRoleModel implements Serializable {
    /**
     * 用户批量增加角色对象
     */
    private List<SysUserRoleKey> addRoleKeys = new ArrayList<>();
    /**
     * 用户批量删除角色对象
     */
    private List<SysUserRoleKey> deleteRoleKeys = new ArrayList<>();
}
