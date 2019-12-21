package com.boyu.erp.platform.sso.authorization;


import com.boyu.erp.platform.sso.baseUtils.Enum.LoginType;
import com.boyu.erp.platform.sso.baseUtils.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**登录失败后的跳转*/
@Component("cstAuthenticationFailureHandler")   //spring security 默认处理器
public class CstAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler { //implements AuthenticationFailureHandler

	private final static Logger logger = LoggerFactory.getLogger(CstAuthenticationFailureHandler.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private SecurityProperties securityProperties;

	//登录失败有很多原因，对应不同的异常
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
	                                    AuthenticationException e) throws IOException, ServletException {
		logger.info("登录失败");

		//如果自定义设定返回的是JSON格式内容,就把内容返回到前台即可
		if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());//服务器内部异常
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(e));
		}else{
			super.onAuthenticationFailure(request,response,e);
		}



	}
}
