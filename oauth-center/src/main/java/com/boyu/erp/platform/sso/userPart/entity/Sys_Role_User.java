package com.boyu.erp.platform.sso.userPart.entity;

import lombok.Data;

@Data
public class Sys_Role_User {

	private Integer id;
	private Integer sys_user_id;
	private Integer sys_role_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSys_user_id() {
		return sys_user_id;
	}

	public void setSys_user_id(Integer sys_user_id) {
		this.sys_user_id = sys_user_id;
	}

	public Integer getSys_role_id() {
		return sys_role_id;
	}

	public void setSys_role_id(Integer sys_role_id) {
		this.sys_role_id = sys_role_id;
	}
}
