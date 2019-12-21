package com.boyu.erp.platform.usercenter.aspect;

import com.boyu.erp.platform.usercenter.components.UpdateTableFile;
import com.boyu.erp.platform.usercenter.constants.GlobalConstants;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.service.system.SysParameterService;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
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

/**
 * @program: boyu-platform_text
 * @description: 修改字段权限过滤
 * @author: clf
 * @create: 2019-06-24 18:43
 */
@Slf4j
@Aspect
@Component
public class UpdateTableFileAspect {
    @Autowired
    private SysParameterService parameterService;

    @Pointcut("@within(com.boyu.erp.platform.usercenter.components.UpdateTableFile) || @annotation(com.boyu.erp.platform.usercenter.components.UpdateTableFile)")
    private void tableFieldFiltration() {
    }

    @Around("tableFieldFiltration()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("        ------进入切点：    ");
        Method m = ((MethodSignature) pjp.getSignature()).getMethod();
        Object[] args = pjp.getArgs();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SysUser user = (SysUser) request.getSession().getAttribute(GlobalConstants.SESSION_SYSTEM_USER);
        log.info("用户拥有权限: " + user.getPrivilegeSet());
        if (!m.isAnnotationPresent(UpdateTableFile.class)) {
            //没有添加注解就放行
            return pjp.proceed();
        }
        UpdateTableFile requsetParamNotNull = m.getAnnotation(UpdateTableFile.class);
        String str = requsetParamNotNull.name();
        if (StringUtils.isEmpty(str)) {
            //添加注解没有参数值放行
            return pjp.proceed();
        }


        String s = str + ParameterString.UPDATA_TABLE_FILE;
        SysParameter parameter = parameterService.findByParameter(s);
        if (parameter == null || StringUtils.isBlank(parameter.getParmVal())) {
            return pjp.proceed();
        } else {
            String[] st = parameter.getParmVal().split(";");

            if(st.length>0){
                for(String sp:st){
                    String  [] param=sp.split("=");
                    if(param[0].indexOf("+")>0){
                        String[] privs = param[0].split("\\+");
                    }
                }
            }
        }

        return null;
    }
}
