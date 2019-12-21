package com.boyu.erp.platform.usercenter.aspect;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.constants.GlobalConstants;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.SystemOperationLog;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.service.system.SystemLogSerivce;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
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
 * @program: boyu-platform
 * @description: 日志注解切面
 * @author: clf
 * @create: 2019-06-03 14:40
 */
@Slf4j
@Aspect
@Component
public class SystemLogAspect {
    @Autowired
    private SystemLogSerivce systemLogSerivce;

    @Pointcut("@within(com.boyu.erp.platform.usercenter.components.SystemLog) || @annotation(com.boyu.erp.platform.usercenter.components.SystemLog)")
    private void systemLog() {
    }

    @AfterReturning(pointcut = "systemLog()", returning = "rest")
    public void aroundSystemLog(JoinPoint pjp, Object rest) {
        try {
            log.info("进入后置返回通知  ----------->>");
            Method m = ((MethodSignature) pjp.getSignature()).getMethod();
            SystemLog systemLog = m.getAnnotation(SystemLog.class);
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            SysUser user = (SysUser) request.getSession().getAttribute(GlobalConstants.SESSION_SYSTEM_USER);

            // 操作描述
            String desc = systemLog.name();
            //没有登陆 或登陆失败
            Long userId;
            Long unitId;
            if (user == null) {
                userId = -1L;
                unitId = -100L;
                desc = "登陆失败";
            } else {
                userId = user.getUserId();
                unitId = user.getOwnerId();
            }
            // 请求URL
            String Url = request.getRequestURL().toString();
            // 请求方法 method
            String meethod = request.getMethod();
            // 打印调用 controller 的全路径以及执行方法
            String controllerUrl = pjp.getSignature().getDeclaringTypeName();
            String Ip = request.getRemoteAddr();
            //获取传入目标方法的参数
            String args = pjp.getArgs()[0].toString();
            StringBuffer stringBuffer = new StringBuffer();
            for (int a = 0; a < pjp.getArgs().length; a++) {
                stringBuffer.append(pjp.getArgs()[a].toString());
            }
            //返回值
            JsonResult respn = (rest instanceof JsonResult) ? (JsonResult) rest : null;
            String respne = respn.getObject().toString();
        /*log.info("操作人Id：  " + userId);
        log.info("操作人组织Id：  " + unitId);
        log.info("操作描述： " + desc);
        log.info("请求路径： " + Url);
        log.info("请求方法： " + meethod);
        log.info("请求全路径： " + controllerUrl);
        log.info("请求Ip： " + Ip);
        log.info("请求参数： " + args);
        log.info("请求返回值： " + respne);
        log.info("请求参数： " + stringBuffer);
        */
            SystemOperationLog systemOperationLog = new SystemOperationLog();
            Long cutDomainUserId = (Long) request.getSession().getAttribute(GlobalConstants.CUTDOMAIN_USERID);
            Long cutDomainUnitId = (Long) request.getSession().getAttribute(GlobalConstants.CUTDOMAIN_USER_UNIT);
            if (cutDomainUserId != null && cutDomainUnitId != null) {
                systemOperationLog.setCutDomain(CommonFainl.TRUE);
                systemOperationLog.setCutDomainUnitId(cutDomainUnitId);
                systemOperationLog.setCutDomainUserId(cutDomainUserId);
            } else {
                systemOperationLog.setCutDomain(CommonFainl.FALSE);
            }
            systemOperationLog.setUserId(userId);
            systemOperationLog.setUnitId(unitId);
            systemOperationLog.setDescribe(desc);
            systemOperationLog.setUrl(Url);
            systemOperationLog.setRequestparameter(stringBuffer.toString());
            systemOperationLog.setResponsevalue(respne);
            systemOperationLog.setIp(Ip);
            systemLogSerivce.addSystemLog(systemOperationLog);
        } catch (Exception e) {
            log.error("记录入日志异常");
        }
    }

}
