package com.boyu.erp.platform.sso.userPart.service;

import com.boyu.erp.platform.sso.userPart.entity.Sys_Role;

import java.util.List;

public interface SysRoleService {

	Sys_Role selectRoleById(int id);

	List<Sys_Role> selectRoles();
}
