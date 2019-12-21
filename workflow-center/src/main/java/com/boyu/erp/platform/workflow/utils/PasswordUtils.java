package com.boyu.erp.platform.workflow.utils;

import org.apache.commons.lang3.StringUtils;

/**
 *  加密工具类
 */
public class PasswordUtils {

    private static final String PASSWORD_SALT="789732472jdasfjo1";

    /**
     * 加密密码
     * @param password
     * @return
     */
    public static String encryptionPassword(String password)
    {
        if(StringUtils.isBlank(password))
        {
            return null;
        }
        StringBuffer sb=new StringBuffer();
        sb.append(password+"=");
        sb.append("salt="+PASSWORD_SALT);
        return EncryptUtil.getMD5(sb.toString());
    }
}
