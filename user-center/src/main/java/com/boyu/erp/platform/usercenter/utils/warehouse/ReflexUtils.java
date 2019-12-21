package com.boyu.erp.platform.usercenter.utils.warehouse;
/**
 * 反射工具类
 * @author HHe
 * @date 2019/11/19 10:00
 */
public class ReflexUtils {
    /**
     * get名称
     * @author HHe
     * @date 2019/11/19 9:57
     */
    public static String getHumpName(String name){
        StringBuffer getName = new StringBuffer();
        return getName.append("get").append(name.substring(0,1).toUpperCase()).append(name.substring(1)).toString();
    }
    /**
     * set名称
     * @author HHe
     * @date 2019/11/19 9:57
     */
    public static String setHumpName(String name){
        StringBuffer getName = new StringBuffer();
        return getName.append("set").append(name.substring(0,1).toUpperCase()).append(name.substring(1)).toString();
    }

}
