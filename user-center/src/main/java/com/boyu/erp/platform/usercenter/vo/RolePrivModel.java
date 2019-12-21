package com.boyu.erp.platform.usercenter.vo;

import com.boyu.erp.platform.usercenter.entity.system.SysPrivilege;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.utils.RequestParamValidate;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 修改角色权限和用户权限Model
 */
@NoArgsConstructor
public class RolePrivModel {
    private String roleId;

    private SysUser user;

    private String add;

    private String delete;

    private List<SysPrivilege> privadd;

    private List<SysPrivilege> privdelete;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAdd() {
        return add = RequestParamValidate.isRes(add);
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = RequestParamValidate.isRes(delete);
    }

    public List<SysPrivilege> getPrivadd() {
        return privadd;
    }

    public void setPrivadd(List<SysPrivilege> privadd) {
        this.privadd = privadd;
    }


    public List<SysPrivilege> getPrivdelete() {
        return privdelete;
    }

    public void setPrivdelete(List<SysPrivilege> privdelete) {
        this.privdelete = privdelete;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

}
