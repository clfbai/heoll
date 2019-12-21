package com.boyu.erp.platform.usercenter.utils.refttable;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @program: boyu-platform
 * @description: 通过反射获取表的所有字段工具类
 * @author: clf
 * @create: 2019-06-13 16:47
 */
public class ReftTable {

    /**
     * 获取对象所有字段 和字段对应的值 (只能获取本类,不能获取其继承字段)
     */
    public static Map<String, Object> getTableFieldAndValue(Object object) throws Exception {
        Map<String, Object> map = null;
        if (map == null) {
            map = new HashMap<>();
        }
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field f : fields) {
            f.setAccessible(true);
            //将当前 对象的属性和属性值放入map(属性=值)
            map.put(f.getName(), f.get(object));
        }
        return map;
    }

    /**
     * 获取对象所有字段  (只能获取本类,不能获取其继承字段)
     */
    public static Set<String> getTableField(Object object) throws Exception {
        Set<String> set = null;
        if (set == null) {
            set = new HashSet<>();
        }
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field f : fields) {
            f.setAccessible(true);
            //将当前 对象字段(属性)放入集合
            set.add(f.getName());
        }
        return set;
    }

    /**
     * 生成过滤后的对象字段集合  (序列化类SimplePropertyPreFilter.getExcludes().add() 这个方法来过滤 应此必须要使字段有值)
     */
    public static Map<String, Object> textTableFieldAndValue(Object object) throws Exception {
        Map<String, Object> map = null;
        if (map == null) {
            map = new HashMap<>();
        }
        Field[] fields = object.getClass().getDeclaredFields();
        int count = 0;
        for (Field f : fields) {
            f.setAccessible(true);
            //将当前 对象的属性和属性值放入map(属性=值)
            map.put(f.getName(), count++);
        }
        return map;
    }


    /**
     * 获取对象所有字段  (只能获取本类,不能获取其继承字段)
     */
    public static Object setObject(Object object) throws Exception {
        List<Object> set = new ArrayList<>();

        Field[] fields = object.getClass().getDeclaredFields();

        for (Field f : fields) {
            f.setAccessible(true);
            //将当前 对象字段(属性)放入集合
            if (f.getName() == null) {

            }
        }
        return set;
    }
}
