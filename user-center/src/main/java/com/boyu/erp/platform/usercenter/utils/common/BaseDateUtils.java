package com.boyu.erp.platform.usercenter.utils.common;

import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * @program: boyu-platform_text
 * @description: 基类赋值工具类
 * @author: clf
 * @create: 2019-06-25 10:39
 */
public class BaseDateUtils {

    /**
     * 给基类赋值
     */
    public static void setBeasDate(Object o, SysUser u, String str) throws Exception {
        /**
         * .getSuperclass() 获取父类
         * */
        Field[] field = o.getClass().getSuperclass().getDeclaredFields();
        for (Field f : field) {
            f.setAccessible(true);
            /**
             * 增加
             * */
            if (CommonFainl.ADD.equalsIgnoreCase(str)) {
                if (f.getName().equals("createBy")) {
                    f.set(o, u.getUserId());
                }
                if (f.getName().equals("createTime")) {
                    f.set(o, new Date());
                }
                if (f.getName().equals("isDel")) {
                    f.set(o, CommonFainl.BTYPESTATUS);
                }
            }
            /**
             * 修改
             * */
            if (CommonFainl.UPDATE.equalsIgnoreCase(str)) {
                if (f.getName().equals("updateBy")) {
                    f.set(o, u.getUserId());
                }
                if (f.getName().equals("updateTime")) {
                    f.set(o, new Date());
                }
            }
            /**
             * 打标删除
             * */
            if (CommonFainl.DELETE.equalsIgnoreCase(str)) {
                if (f.getName().equals("updateBy")) {
                    f.set(o, u.getUserId());
                }
                if (f.getName().equals("updateTime")) {
                    f.set(o, new Date());
                }
                if (f.getName().equals("isDel")) {
                    f.set(o, CommonFainl.FAILSTATUS);
                }
            }
        }
    }
}
