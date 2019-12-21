package com.boyu.erp.platform.usercenter.utils;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class StringUtils {
    /**
     * StringUtils工具类方法
     * 获取一定长度的随机字符串，范围0-9，a-z
     *
     * @param length：指定字符串长度
     * @return 一定长度的随机字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String getRandomString() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static boolean isNotEmpty(String object) {
        if (object != null && !"null".equals(object) && !"".equals(object)) {
            return true;
        }
        return false;

    }

    public static boolean NullEmpty(String object) {
        if (object == null || "".equals(object) || "null".equals(object.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 验证 集合 所有字段 参数不为空
     */
    public static boolean isParamNotEmpty(List<Object> list) {
        boolean b = false;
        for (Object m : list) {
            if (m instanceof String) {
                if (NullEmpty((String) m)) {
                    b = true;
                } else {
                    return false;
                }
            }
            if (m instanceof Integer || m instanceof Long || m instanceof Float) {
                if (m != null) {
                    b = true;
                } else {
                    return false;
                }
            }
        }
        return b;
    }
}
