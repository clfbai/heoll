package com.boyu.erp.platform.usercenter.vo;

import com.boyu.erp.platform.usercenter.entity.system.SysRole;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.utils.RequestParamValidate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 权限角色对应修改操作Model
 */
@Data
@ToString
@NoArgsConstructor
public class PrivModel {
    private String privId;

    private String add;

    private String delete;

    private SysUser user;

    private List<SysRole> roleadd;

    private List<SysRole> roledelete;


    public void setAdd(String add) {
        this.add = RequestParamValidate.isRes(add);
    }

    public String getAdd() {
        return add;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = RequestParamValidate.isRes(delete);
    }


}
