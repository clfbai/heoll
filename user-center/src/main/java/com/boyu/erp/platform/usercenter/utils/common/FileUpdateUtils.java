package com.boyu.erp.platform.usercenter.utils.common;

import com.boyu.erp.platform.usercenter.constants.ResultCode;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysPrivilege;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.service.system.SysParameterService;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform_text
 * @description: 检查是否有权修改某些字段
 * @author: clf
 * @create: 2019-06-24 19:45
 */
@Slf4j
@Component
public class FileUpdateUtils {
    @Autowired
    private SysParameterService parameterService;

    /**
     * @param o    类
     * @param pojo 类名
     * @description: 判断是否具有某些字段的修改权限
     * @author: CLF
     */
    public void isNotUpdate(Object o, String pojo, SysUser u) {
        /**
         * 需要判断map
         **/
        Map<String, Object> map = getTableFlie(o, pojo);
        /**
         * 修能改字段集合(有相应的权限)
         **/
        List<String> list = isNotUpdateFile(map, u);
        /**
         * 不能修改字段集合(没有相应的权限)
         **/
        List<String> rest = noUpdateFile(map, u);

        if (map != null && list == null || list.size() <= 0) {
            throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "你还没有权限修改");
        }
        Field[] field = o.getClass().getDeclaredFields();

        for (Field f : field) {
            f.setAccessible(true);
            for (String s : rest) {
                if (f.getName().equals(s)) {
                    throw new ServiceException(ResultCode.ILLEGAL_ARGUMENT_ERROR, "你还没有权限修改:" + f.getName() + "的值");
                }
            }
        }

    }

    /**
     * 需要权限修改的字段集合
     */
    public Map<String, Object> getTableFlie(Object o, String pojo) {
        String param = pojo + ParameterString.UPDATA_TABLE_FILE;
        SysParameter parameter = parameterService.findByParameter(param);
        Map<String, Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        //参数不为空
        if (param != null) {
            String[] st = parameter.getParmVal().split(";");
            if (st.length > 0) {
                for (String sp : st) {
                    String[] params = sp.split("=");
                    if (params[0].indexOf("+") > 0) {
                        String[] privs = params[0].split("\\+");
                        for (String s : privs) {
                            //需要判断的字段
                            list.add(s);

                        }
                    }
                    map.put(params[1], list);
                    log.info("修改的key: " + params[1]);
                }
            }
        }
        return map;
    }

    /**
     * 有权限修改的字段集合
     */
    public List<String> isNotUpdateFile(Map<String, Object> map, SysUser u) {
        List<String> list = new ArrayList();
        if (map != null) {
            for (String s : map.keySet()) {
                for (SysPrivilege p : u.getPrivilegeSet()) {
                    if (s.equalsIgnoreCase(p.getPrivId())) {
                        //可以修改的 字段 对应的 key
                        list.add(s);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 没有权限修改的字段集合
     */
    public List<String> noUpdateFile(Map<String, Object> map, SysUser u) {
        List<String> list = isNotUpdateFile(map, u);
        List<String> rest = new ArrayList<>();
        if (list != null && map != null && list.size() > 0) {
            for (String s : list) {
                String r = s;
                for (String mas : map.keySet()) {
                    if (s.equals(mas)) {
                        r = "";
                    }
                }
                if (StringUtils.isNotBlank(r)) {
                    //没有权限能修改的字段
                    rest.addAll((List<String>) map.get(r));
                    log.info("没有的权限： " + r);
                }
            }
        }
        if (list == null || list.size() <= 0) {
            for (String mas : map.keySet()) {
                rest.addAll((List<String>) map.get(mas));
                log.info("没有所有权限： " + mas);
            }
        }
        return rest;
    }
}
