package com.boyu.erp.platform.usercenter.vo.system;

import com.boyu.erp.platform.usercenter.entity.system.SysPrivilege;
import com.boyu.erp.platform.usercenter.entity.system.SysRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UnitPrivModel implements Serializable {

    /**
     * 操作的组织id
     */
    private Long unitId;

    private String add;

    private String delete;


    /**
     * 角色数据范围类型
     */
    private String scopeType;

    /**
     * 角色对应组织范围
     */
    private String roleUnit;

    /**
     * 添加的组织权限
     */
    private List<SysPrivilege> addPrivilege = new ArrayList<>();

    /**
     * 删除的组织权限
     */
    private List<SysPrivilege> deletePrivilege = new ArrayList<>();

    /**
     * 添加的组织角色
     */
    private List<SysRole> addUnitRole=new ArrayList<>();

    /**
     * 删除的组织角色
     */
    private  List<SysRole> deleteUnitRole=new ArrayList<>();
}
