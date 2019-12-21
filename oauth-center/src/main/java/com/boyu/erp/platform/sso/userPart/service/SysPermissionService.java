package com.boyu.erp.platform.sso.userPart.service;

import com.boyu.erp.platform.sso.userPart.entity.Sys_Permission;

import java.util.List;

public interface SysPermissionService {

	Sys_Permission selectSysPermissionById(int id);

	List<Sys_Permission> selectSysPermissions();
}
