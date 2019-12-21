package com.boyu.erp.platform.usercenter.controller.base;

import com.boyu.erp.platform.usercenter.constants.GlobalConstants;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 基类controller
 */
public class BaseController {


    /**
     * 获取用户对象
     *
     * @param request
     * @return
     */
    public SysUser getSessionSysUser(HttpServletRequest request) throws ServiceException {

        SysUser sysUser = (SysUser) request.getSession().getAttribute(GlobalConstants.SESSION_SYSTEM_USER);
        if (sysUser == null) {
            throw new ServiceException("201", "session失效请重新登录");
        }
        return sysUser;
    }

    /**
     * 得到request对象
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 判断并获取用户对象
     *
     * @param request
     * @return
     */
    public SysUser isNullUser(HttpServletRequest request) throws ServiceException {
        SysUser sysUser = (SysUser) request.getSession().getAttribute(GlobalConstants.SESSION_SYSTEM_USER);
        if (sysUser == null) {
            throw new ServiceException("201", "session失效请登录");
        }
        return sysUser;
    }


}
