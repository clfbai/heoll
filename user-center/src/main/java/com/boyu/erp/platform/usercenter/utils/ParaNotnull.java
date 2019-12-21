package com.boyu.erp.platform.usercenter.utils;


import java.lang.reflect.Field;

public class ParaNotnull {
    //判断对象属性是否为空
    public static boolean isNotnull(Object obj) throws Exception {
        Class stuCla = (Class) obj.getClass();
        Field[] fs = stuCla.getDeclaredFields();
        for (Field f : fs) {
            f.setAccessible(true);// 设置属性是可以访问的(私有的也可以)
            Object val = f.get(obj);
            if (val == null) {
                return false;
            }
        }
        return true;
    }
}
