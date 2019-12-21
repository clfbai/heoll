package com.boyu.erp.platform.usercenter.utils.common;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @program: boyu-platform
 * @description: 返回不分页手动封装
 * @author: clf
 * @create: 2019-05-27 09:56
 */
public class RestulMap {

    public static Map<String, Object> getResut(Object o) {
        Map<String, Object> map = new HashMap<>();
        if (o != null) {
            List list = (o instanceof List) ? (List) o : null;
            Set set = (o instanceof Set) ? (Set) o : null;
            map.put("list", new ArrayList<>());
            map.put("size", 0);
            if (CollectionUtils.isNotEmpty(list)) {
                map.put("size", list.size());
                map.put("list", list);
            }
            if (CollectionUtils.isNotEmpty(set)) {
                map.put("size", set.size());
                map.put("list", set);
            }
            return map;
        } else {
            return null;
        }
    }
}
