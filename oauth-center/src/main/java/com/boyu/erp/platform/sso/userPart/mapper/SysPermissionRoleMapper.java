package com.boyu.erp.platform.sso.userPart.mapper;

import com.boyu.erp.platform.sso.userPart.entity.Sys_Permission_Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SysPermissionRoleMapper {

	@Select("select * from sys_permission_role where role_id = #{role_id}")
	List<Sys_Permission_Role> selectPermissionRole(int role_id);

}
