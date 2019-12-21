package com.boyu.erp.platform.sso.userPart.mapper;

import com.boyu.erp.platform.sso.userPart.entity.Sys_Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SysPermissionMapper {

	@Select("select * from sys_permission where id = #{id}")
	Sys_Permission selectSysPermissionById(int id);

	@Select("select * from sys_permission where 1=1")
	List<Sys_Permission> selectSysPermissions();
}
