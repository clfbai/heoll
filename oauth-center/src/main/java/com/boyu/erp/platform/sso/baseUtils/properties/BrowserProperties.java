package com.boyu.erp.platform.sso.baseUtils.properties;


import com.boyu.erp.platform.sso.baseUtils.Enum.LoginType;

public class BrowserProperties {

	//自定义登录用到的页面是哪个页面
	private String signInPage;

	public String getSignInPage() {
		return signInPage;
	}

	public void setSignInPage(String signInPage) {
		this.signInPage = signInPage;
	}


	//自定义返登录成功/失败回数据类型为json类型
	private LoginType loginType = LoginType.JSON;

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}
}
