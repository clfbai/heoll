package com.boyu.erp.platform.sso.userPart.service.impl;

import com.boyu.erp.platform.sso.userPart.mapper.SysPermissionRoleMapper;
import com.boyu.erp.platform.sso.userPart.service.SysPermissionRoleService;
import com.boyu.erp.platform.sso.userPart.entity.Sys_Permission_Role;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@Log4j
public class BaseSysPermissionRoleService implements SysPermissionRoleService {

	private final com.boyu.erp.platform.sso.userPart.mapper.SysPermissionRoleMapper SysPermissionRoleMapper;

	public BaseSysPermissionRoleService(SysPermissionRoleMapper SysPermissionRoleMapper){
		this.SysPermissionRoleMapper = SysPermissionRoleMapper;
	}
	public List<Sys_Permission_Role> selectPermissionRole(int role_id){
		return SysPermissionRoleMapper.selectPermissionRole(role_id);
	}
}
