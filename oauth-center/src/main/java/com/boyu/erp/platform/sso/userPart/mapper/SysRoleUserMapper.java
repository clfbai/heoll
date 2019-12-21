package com.boyu.erp.platform.sso.userPart.mapper;

import com.boyu.erp.platform.sso.userPart.entity.Sys_Role_User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SysRoleUserMapper {

	@Select("select * from sys_role_user where sys_user_id = #{user_id}")
	List<Sys_Role_User> selectByUser_id(int user_id);
}
