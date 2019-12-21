package com.boyu.erp.platform.usercenter.aspect;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.boyu.erp.platform.usercenter.components.FieldAuthority;
import com.boyu.erp.platform.usercenter.constants.GlobalConstants;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysPrivilege;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.service.system.SysParameterService;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import com.boyu.erp.platform.usercenter.utils.common.TableFieldPriv;
import com.boyu.erp.platform.usercenter.utils.common.TablePrivRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @description: 过滤表字段
 * @author: CLF
 * @create:
 */
@Slf4j
@Aspect
@Component
public class FieldAuthorityAspect {

    @Autowired
    private SysParameterService parameterService;

    @Autowired
    private TablePrivRedisUtils tablePrivRedisUtils;


    @Pointcut("@within(com.boyu.erp.platform.usercenter.components.FieldAuthority) || @annotation(com.boyu.erp.platform.usercenter.components.FieldAuthority)")
    private void tableFieldFiltration() {
    }

    @Around("tableFieldFiltration()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("进入表字段权限切面：    ");
        Method m = ((MethodSignature) pjp.getSignature()).getMethod();
        Object[] args = pjp.getArgs();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SysUser user = (SysUser) request.getSession().getAttribute(GlobalConstants.SESSION_SYSTEM_USER);
        //log.info("用户拥有权限: " + user.getPrivilegeSet());

        if (!m.isAnnotationPresent(FieldAuthority.class)) {
            //没有添加注解就放行
            return pjp.proceed();
        }
        FieldAuthority requsetParamNotNull = m.getAnnotation(FieldAuthority.class);

        String str = requsetParamNotNull.name();
        if (StringUtils.isEmpty(str)) {
            //添加注解没有参数值放行
            return pjp.proceed();
        }
        String s = str + ParameterString.CREAT_TABLE;
        //log.info("获得拼接参数Id：                            " + s);
        //获得参数对象
        SysParameter parameter = parameterService.findByParameter(s);

        List<TableFieldPriv> tableFieldList = new ArrayList<>();
        //需要判断字段权限集合
        String[] priv;
        //表字段集合
        List<String> tableField = new ArrayList<>();
        String[] param;
        if (parameter != null) {
            String list = parameter.getParmVal();
            param = list.split(";");
            for (String ps : param) {
                log.info("获得表字段对应权限Id:   " + ps);
                if (StringUtils.isNotEmpty(ps)) {
                    priv = ps.split("=");
                    if (priv[0].indexOf("+") > 0) {
                        log.info("进入检查");
                        String[] privs = priv[0].split("\\+");
                        for (String sfg : privs) {
                            TableFieldPriv tableFieldPriv = new TableFieldPriv(sfg, priv[1]);
                            tableFieldList.add(tableFieldPriv);
                        }
                    } else {
                        TableFieldPriv tableFieldPriv = new TableFieldPriv(priv[0], priv[1]);
                        tableFieldList.add(tableFieldPriv);
                    }
                }
            }
        }
        //需要过滤字段集合
        Set<TableFieldPriv> set = new HashSet<TableFieldPriv>();
        //获取用户需要过滤字段
        getPrivset(set, user.getPrivilegeSet(), tableFieldList);
        //过滤json对象
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        if (set.size() > 0) {
            for (TableFieldPriv ps : set) {
                filter.getExcludes().add(ps.getTableFiled());
                log.info("需要过滤字段: " + ps.getTableFiled());
            }
        } else {
            log.info("拥有所有权限 ");
        }
        Object obj = pjp.proceed();
       // log.info("代理获得结果           " + obj.toString());
        JsonResult jsonResult = (JsonResult) obj;
        if (jsonResult.getObject() instanceof Object) {
            Map<String, Object> map = new HashMap<>();
            /*  前台暂时不需要 过滤后的对象
            Map<Object, Object> ms = tablePrivRedisUtils.getPrivTable();
            for (Object o : ms.keySet()) {
                String strPriv = (String) o;
                String vo = requsetParamNotNull.name() + "Vo";
                log.info("   拼接：   " + vo);
                if (vo.equals(strPriv)) {
                    Class<?> classzz = Class.forName(ms.get(strPriv).toString());
                    Object os = classzz.newInstance();
                    map.put("file", ReftTable.textTableFieldAndValue(os));
                    jsonResult.setObject(map);
                }
            }*/
            map.put("file", set);
            map.put("original", jsonResult.getObject());
            jsonResult.setObject(map);
        }
        Object o = jsonResult;
        //o是Java对象
        String result = JSONObject.toJSONString(o, filter);
        return JSONObject.parseObject(result);
    }

    private void getPrivset(Set<TableFieldPriv> set, List<SysPrivilege> privilegeSet, List<TableFieldPriv> tableFieldList) {
        for (TableFieldPriv ps : tableFieldList) {
            log.info("检查字段:  " + ps.getTableFiled());
            m:
            for (SysPrivilege privilege : privilegeSet) {
                //判断用户有无字段权限
                if (privilege.getPrivId().equalsIgnoreCase(ps.getPrivId())) {
                    //用户有该项权限 判断集合中是否存在要删除对象
                    if (set.size() > 0) {
                        for (TableFieldPriv ts : set) {
                            if (ts.getTableFiled() == ps.getTableFiled()) {
                                //如果用户拥有该项权限将而集合中已存在该项权限对应的字段 则 将set集合中字段对应的对象去掉 并跳出循环
                                set.remove(ps);
                                break m;
                            }
                        }
                    }
                    break m;
                }
                //没有权限添加对象
                set.add(ps);
            }
            //用户没有任何字段权限
            if (privilegeSet.size() == 0) {
                set.add(ps);
            }
        }
    }
}