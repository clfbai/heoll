package com.boyu.erp.platform.sso.userPart.service;

import com.boyu.erp.platform.sso.userPart.entity.Sys_Role_User;

import java.util.List;

public interface SysRoleUserService {

	List<Sys_Role_User> selectByUser_id(int user_id);
}
