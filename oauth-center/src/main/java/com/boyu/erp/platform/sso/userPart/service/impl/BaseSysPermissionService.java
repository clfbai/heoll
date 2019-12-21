package com.boyu.erp.platform.sso.userPart.service.impl;

import com.boyu.erp.platform.sso.userPart.entity.Sys_Permission;
import com.boyu.erp.platform.sso.userPart.mapper.SysPermissionMapper;
import com.boyu.erp.platform.sso.userPart.service.SysPermissionService;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@Log4j
public class BaseSysPermissionService implements SysPermissionService {

	private final SysPermissionMapper sysPermissionMapper;

	public BaseSysPermissionService(SysPermissionMapper sysPermissionMapper){
		this.sysPermissionMapper = sysPermissionMapper;
	}

	@Override
	public Sys_Permission selectSysPermissionById(int id) {
		return sysPermissionMapper.selectSysPermissionById(id);
	}

	@Override
	public List<Sys_Permission> selectSysPermissions() {
		return sysPermissionMapper.selectSysPermissions();
	}
}
