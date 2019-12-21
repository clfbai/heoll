package com.boyu.erp.platform.sso.userPart.service.impl;

import com.boyu.erp.platform.sso.userPart.mapper.SysRoleUserMapper;
import com.boyu.erp.platform.sso.userPart.service.SysRoleUserService;
import com.boyu.erp.platform.sso.userPart.entity.Sys_Role_User;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@Log4j
public class BaseSysRoleUserService implements SysRoleUserService {

	private final com.boyu.erp.platform.sso.userPart.mapper.SysRoleUserMapper SysRoleUserMapper;

	public BaseSysRoleUserService(SysRoleUserMapper SysRoleUserMapper){
		this.SysRoleUserMapper = SysRoleUserMapper;
	}


	@Override
	public List<Sys_Role_User> selectByUser_id(int user_id) {
		return SysRoleUserMapper.selectByUser_id(user_id);
	}
}
