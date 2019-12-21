package com.boyu.erp.platform.usercenter.vo;

import lombok.Data;

@Data
public class LoginModel {

    //验证码
    private String validateCode;

    private String prsnlCode;
    //密码
    private String userPwd;

    //领域
    private Long domainId;

    //原密码
    private String rawPwd;

    //领域代码
    private String  domain;
}
