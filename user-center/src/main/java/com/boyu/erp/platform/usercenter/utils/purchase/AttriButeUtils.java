package com.boyu.erp.platform.usercenter.utils.purchase;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.purchase.VdrAttrDef;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @program: boyu-platform_text
 * @description: 传入自定义属性的list，封装成需要的对象返回
 * 主要用于查询到的自定义属性为空
 * @author: clf
 * @create: 2019-7-8 9:38
 */
@Slf4j
@Component
public class AttriButeUtils {

    public List<Map<String, Object>> Attrs(Object o) throws Exception {
        List<Object> list = (List<Object>) o;

        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> map = new HashMap<String, Object>();

        for (Object a : list) {
            Field[] field = a.getClass().getDeclaredFields();
            for (Field f : field) {
                f.setAccessible(true);
                if ("attrType".equals(f.getName())) {
                    map.put(f.get(a).toString(), "");
                }
            }
        }
        result.add(map);
        return result;
    }

    /**
     * 打印对象中的字段
     */
    public static void Attr(Object o) throws Exception {
            String str = "";
            Field[] field = o.getClass().getDeclaredFields();
            for (Field f : field) {
                f.setAccessible(true);
                str+=f.getName()+",";
            }
            log.info("》》》》》》》》》》》》》》》"+str);
    }
}
