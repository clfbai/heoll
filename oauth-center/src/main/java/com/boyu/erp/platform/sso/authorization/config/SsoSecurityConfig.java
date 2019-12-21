package com.boyu.erp.platform.sso.authorization.config;

import com.boyu.erp.platform.sso.authorization.csrfHeader.CsrfHeaderFilter;
import com.boyu.erp.platform.sso.baseUtils.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
public class SsoSecurityConfig extends WebSecurityConfigurerAdapter {

	private final static Logger logger = LoggerFactory.getLogger(SsoSecurityConfig.class);

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	protected AuthenticationFailureHandler cstAuthenticationFailureHandler;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		logger.info("用自己定义的usersevices来验证用户");
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//把登录方式改成表单登录的形式
		logger.info("-----定义表单登录方式+自定义成功跳转方法+自定义登录页面----");
		/**/
		http    //.csrf().disable()先禁用跨站访问功能
				.formLogin()//表单登录
				.loginPage("/authentication/require")//自定义登录跳转方法
				.loginProcessingUrl("/authentication/form")// 提交登录表单地址(与登录页中提交的地址一致，就可以提交到登录验证服务MyUserDetailsService 中)
				//.successHandler(cstAuthenticationSuccessHandler)//成功后跳转自定义方法
				.failureHandler(cstAuthenticationFailureHandler)//失败后跳转自定义方法
				.and()
				.httpBasic()
				.and()
				.authorizeRequests()
				.antMatchers("/authentication/require",securityProperties.getBrowser().getSignInPage()).permitAll()//这个页面不需要身份认证，其他都需要
				.anyRequest()//任何请求
				.authenticated()//都需要身份认证
				.and()
				.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);//把CSRFtoken设定到cookie
		/**/
		//http.formLogin().and().authorizeRequests().anyRequest().authenticated();
	}
}
