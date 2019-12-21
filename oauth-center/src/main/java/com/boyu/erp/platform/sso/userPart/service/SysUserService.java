package com.boyu.erp.platform.sso.userPart.service;

import com.boyu.erp.platform.sso.userPart.entity.Sys_User;

public interface SysUserService {

	Sys_User selectById(int id);

	Sys_User selectByUsername(String username);
}
