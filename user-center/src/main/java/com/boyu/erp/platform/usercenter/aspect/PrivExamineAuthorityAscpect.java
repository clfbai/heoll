package com.boyu.erp.platform.usercenter.aspect;

import com.boyu.erp.platform.usercenter.components.PrivExamineAuthority;
import com.boyu.erp.platform.usercenter.constants.GlobalConstants;
import com.boyu.erp.platform.usercenter.entity.system.SysPrivilege;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.service.system.UserScopeServer;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
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
import java.util.List;

/**
 * 类描述:
 * 权限检查切面(检查用户在某个组织有没有指定权限Id,主要是用来判断用户在组织操作权限)
 *
 * @Description:
 * @auther: CLF
 * @date: 2019/8/26 10:48
 */
@Slf4j
@Aspect
@Component
public class PrivExamineAuthorityAscpect {
    @Autowired
    private UsersService usersService;
    @Autowired
    private UserScopeServer userScopeServer;

    //切入点
    @Pointcut("@within(com.boyu.erp.platform.usercenter.components.PrivExamineAuthority) || @annotation(com.boyu.erp.platform.usercenter.components.PrivExamineAuthority)")
    private void privExamine() {
    }

    //环绕通知
    @Around("privExamine()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Method m = ((MethodSignature) pjp.getSignature()).getMethod();
        Object[] args = pjp.getArgs();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SysUser user = (SysUser) request.getSession().getAttribute(GlobalConstants.SESSION_SYSTEM_USER);
        //当前登录组织权限集合
        List<SysPrivilege> set = user.getPrivilegeSet();

        if (!m.isAnnotationPresent(PrivExamineAuthority.class)) {
            //没有添加注解就放行
            return pjp.proceed();
        }
        PrivExamineAuthority requsetParamNotNull = m.getAnnotation(PrivExamineAuthority.class);
        String privId = requsetParamNotNull.privId();
        if (StringUtils.isNotBlank(privId)) {
            //添加组织权限Id为空放行
            return pjp.proceed();
        }

        return pjp.proceed();
    }
}
