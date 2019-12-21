package com.boyu.erp.platform.sso.baseUtils.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

//读取所有配置文件中以'dkd.security'开头的配置项
@ConfigurationProperties(prefix = "dkd.security")
public class SecurityProperties {

	BrowserProperties browser = new BrowserProperties();

	public BrowserProperties getBrowser() {
		return browser;
	}

	public void setBrowser(BrowserProperties browser) {
		this.browser = browser;
	}

}
