package com.boyu.erp.platform.sso.userPart.service.impl;

import com.boyu.erp.platform.sso.userPart.mapper.SysRoleMapper;
import com.boyu.erp.platform.sso.userPart.service.SysRoleService;
import com.boyu.erp.platform.sso.userPart.entity.Sys_Role;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@Log4j
public class BaseSysRoleService implements SysRoleService {

	private final SysRoleMapper sysRoleMapper;

	public BaseSysRoleService(SysRoleMapper sysRoleMapper){
		this.sysRoleMapper = sysRoleMapper;
	}

	@Override
	public Sys_Role selectRoleById(int id) {
		return sysRoleMapper.selectRoleById(id);
	}

	@Override
	public List<Sys_Role> selectRoles() {
		return sysRoleMapper.selectRoles();
	}
}
