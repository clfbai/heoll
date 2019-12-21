package com.boyu.erp.platform.sso.authorization;

import com.boyu.erp.platform.sso.userPart.entity.Sys_Role;
import com.boyu.erp.platform.sso.userPart.entity.Sys_Role_User;
import com.boyu.erp.platform.sso.userPart.entity.Sys_User;
import com.boyu.erp.platform.sso.userPart.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {

	private final static Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

	/********注入自定义的用户service********/
	@Autowired
	private final SysUserService sysUserService;
	@Autowired
	private SysPermissionService sysPermissionService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleUserService sysRoleUserService;
	@Autowired
	private SysPermissionRoleService sysPermissionRoleService;

	@Autowired
	MyUserDetailsService(SysUserService sysUserService){
		this.sysUserService = sysUserService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//表单登录
		Sys_User sysUser = sysUserService.selectByUsername(username);
		if (sysUser == null) {
			throw new UsernameNotFoundException("用户不存在！");
			//返回方式二：返回带失败原因的数据对象
			//return new User(username, userEntity.getPassword(), true,true,true,true,
			//		AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
		}else{
			logger.info("用户存在，用户:" + username);
			//把用户的角色赋给该用户当作该用户的权限
			List<Sys_Role_User> sruList = sysRoleUserService.selectByUser_id(sysUser.getId());
			List<GrantedAuthority> grantedAuthorities = new ArrayList <>();
			for(Sys_Role_User ru : sruList){
				Sys_Role role = sysRoleService.selectRoleById(ru.getSys_role_id());
				logger.info(username+"-->role:"+role.getName());
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
				//1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
				grantedAuthorities.add(grantedAuthority);
			}
			return new User(sysUser.getUsername(), sysUser.getPassword(), grantedAuthorities);
		}
	}


	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		return null;
	}
}
