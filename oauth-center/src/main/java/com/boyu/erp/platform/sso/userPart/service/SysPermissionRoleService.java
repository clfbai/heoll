package com.boyu.erp.platform.sso.userPart.service;

import com.boyu.erp.platform.sso.userPart.entity.Sys_Permission_Role;

import java.util.List;

public interface SysPermissionRoleService {

	List<Sys_Permission_Role> selectPermissionRole(int role_id);

}
