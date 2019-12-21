package com.boyu.erp.platform.usercenter.interceptor;

import com.alibaba.fastjson.JSON;
import com.boyu.erp.platform.usercenter.constants.GlobalConstants;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.NoLoginException;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  全局登录拦截器
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor
{
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
        {
            log.info("[LoginInterceptor][preHandle] web请求的地址为 {}",request.getRequestURI().toString());

            SysUser sysUser = (SysUser)request.getSession().getAttribute(GlobalConstants.SESSION_SYSTEM_USER);

            log.info("[LoginInterceptor][preHandle] 从session中获取对象 {}",JSON.toJSON(sysUser));

            if (sysUser == null)
            {
                log.info("[LoginInterceptor][preHandle] 从session中没有获取对象");
                throw new NoLoginException(JsonResultCode.NO_LOGIN,"请先登录");
            }
            return true;
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            log.info("postHandle...");
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            log.info("afterCompletion...");
        }
}