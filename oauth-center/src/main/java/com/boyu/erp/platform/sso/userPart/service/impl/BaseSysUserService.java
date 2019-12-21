package com.boyu.erp.platform.sso.userPart.service.impl;

import com.boyu.erp.platform.sso.userPart.service.SysUserService;
import com.boyu.erp.platform.sso.userPart.entity.Sys_User;
import com.boyu.erp.platform.sso.userPart.mapper.SysUserMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@Log4j
public class BaseSysUserService implements SysUserService {

	private final SysUserMapper sysUserMapper;

	public BaseSysUserService(SysUserMapper sysUserMapper){
		this.sysUserMapper = sysUserMapper;
	}

	@Override
	public Sys_User selectById(int id) {
		return sysUserMapper.selectById(id);
	}

	@Override
	public Sys_User selectByUsername(String username) {
		return sysUserMapper.selectByUsername(username);
	}
}
