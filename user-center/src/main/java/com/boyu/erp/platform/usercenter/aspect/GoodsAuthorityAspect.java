package com.boyu.erp.platform.usercenter.aspect;

import com.boyu.erp.platform.usercenter.components.GoodsBrandPrivAuthority;
import com.boyu.erp.platform.usercenter.constants.GlobalConstants;
import com.boyu.erp.platform.usercenter.entity.system.SysPrivilege;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.service.system.UsersService;
import com.boyu.erp.platform.usercenter.utils.ParameterString;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
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
 * @program: boyu-platform
 * @description: 品牌操作全新切面
 * @author: clf
 * @create: 2019-05-22 14:42
 */
@Slf4j
@Aspect
@Component
public class GoodsAuthorityAspect {
    @Autowired
    private UsersService usersService;

    @Pointcut("@within(com.boyu.erp.platform.usercenter.components.GoodsBrandPrivAuthority) || @annotation(com.boyu.erp.platform.usercenter.components.GoodsBrandPrivAuthority)")
    private void GoodsBrandPriv() {
    }

    @Around("GoodsBrandPriv()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SysUser user = (SysUser) request.getSession().getAttribute(GlobalConstants.SESSION_SYSTEM_USER);
        if (usersService.getAdmin(user) != null && usersService.getAdmin(user).getOprId() == CommonFainl.ADMIN) {
            return pjp.proceed();
        }
        log.info("        ------进入切点：    ");
        Method m = ((MethodSignature) pjp.getSignature()).getMethod();
        Object[] args = pjp.getArgs();

        log.info("" + user.getPrivilegeSet());
        if (!m.isAnnotationPresent(GoodsBrandPrivAuthority.class)) {
            //没有添加注解就放行
            return pjp.proceed();
        }
        GoodsBrandPrivAuthority goods = m.getAnnotation(GoodsBrandPrivAuthority.class);
        String str = goods.name();
        if (StringUtils.isEmpty(str)) {
            //添加注解没有参数值放行
            return pjp.proceed();
        }
        String s = null;
        String st = null;
        //操作品牌分组 需要权限
        if (str.endsWith("Bg")) {
            s = verifyPriv(user.getPrivilegeSet(), ParameterString.BRAND_BG_PRIV);
            st = verifyPriv(user.getPrivilegeSet(), ParameterString.GOODS_BRAND_PRIV);
            if (StringUtils.isNotEmpty(s) && StringUtils.isNotEmpty(st)) {
                return pjp.proceed();
            } else {
                return new JsonResult("201", "尚未获得“brand_bg_priv_crud”权限", "");
            }
        }
        /**
         * 操作品牌 需要权限
         * str.endsWith("Brand") endsWith()方法判断str是否以Brand结尾即 Brand是否是str 的后缀
         * */
        if (str.endsWith("Brand")) {
            System.out.println("操作是?: " + str);
            st = verifyPriv(user.getPrivilegeSet(), ParameterString.GOODS_BRAND_PRIV);

            System.out.println("过滤结果?: " + st);
            if (StringUtils.isNotEmpty(st)) {
                return pjp.proceed();
            }
        }


        return new JsonResult("201", "尚未获得“goods_priv_crud”权限", "");
    }

    private String verifyPriv(List<SysPrivilege> privilegeSet, String brandBgPriv) {
        String s = null;
        for (SysPrivilege p : privilegeSet) {
            if (brandBgPriv.equalsIgnoreCase(p.getPrivId())) {
                 System.out.println("有权限码"+p.getPrivId());
                s = brandBgPriv;
            }
        }
        return s;
    }


}
