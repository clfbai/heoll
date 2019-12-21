package com.boyu.erp.platform.sso.authorization.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

//认证服务器
@Configuration
@EnableAuthorizationServer
public class SsoAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private final static Logger logger = LoggerFactory.getLogger(SsoAuthorizationServerConfig.class);

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		logger.info("创建两个客户端,为这两个客户端发送授权，或者通过配置文件配置");
		clients.inMemory()
				.withClient("appa")
				.secret("appa_ret")
				.authorizedGrantTypes("authorization_code", "refresh_token")
				.scopes("all")
				.and()
				.withClient("appb")
				.secret("appb_ret")
				.authorizedGrantTypes("authorization_code", "refresh_token")
				.scopes("all");
	}
	/** JWT令牌配置有关的两个 bean **/
	@Bean
	public TokenStore jwtTokenStore(){
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter(){
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("ssodemo");//tokenKey
		logger.info("jwt的秘钥是：ssodemo");
		return converter;
	}

	//生成令牌
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(jwtTokenStore()).accessTokenConverter(jwtAccessTokenConverter());
	}


	//安全配置
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//要访问授权服务器的tokenKey（签名秘钥）时，要经过身份认证
		//默认秘钥是无法访问的，这样设置后，只要经过身份认证后，就可以拿到秘钥
		security.tokenKeyAccess("isAuthenticated()");
	}



}
