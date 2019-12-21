package com.boyu.erp.platform.sso.userPart.mapper;

import com.boyu.erp.platform.sso.userPart.entity.Sys_User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SysUserMapper {

	@Select("select * from sys_user where username = #{username}")
	Sys_User selectByUsername(@Param("username") String username);

	@Select("select * from sys_user where id = #{id}")
	Sys_User selectById(@Param("id") int id);

}
