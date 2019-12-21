package com.boyu.erp.platform.usercenter.utils;

import java.util.Date;

public class RandomStringUtils {
    public static String randomNumeric(int length) {
        if (length <= 0) {
            length = 6;
        }
        int n = (int) Math.pow(10, length);
        int m = 0;
        int temp = m + (int) (Math.random() * (n + 1 - m));
        return String.valueOf(temp);
    }

    /**
     * 功能描述: 按照时间规则生成字符串
     *
     * @param length (位数)
     * @return:
     * @auther: CLF
     * @date: 2019/11/27 9:55
     */
    public static String crateUuid(int length) {
        String time = DateUtil.dateToString(new Date(), "yyyyMMddHHmmss");
        String num = randomNumeric(length);
        return time + num;
    }

}
