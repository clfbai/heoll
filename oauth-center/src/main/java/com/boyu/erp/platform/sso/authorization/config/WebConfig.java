package com.boyu.erp.platform.sso.authorization.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*跨域设置*/
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	public void addCorsMappings(CorsRegistry registry){
		registry.addMapping("/**");
	}
}
