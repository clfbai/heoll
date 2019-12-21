package com.boyu.erp.platform.workflow.exception;

import com.boyu.erp.platform.workflow.result.JsonResult;
import com.boyu.erp.platform.workflow.result.JsonResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常
 * @author Administrator
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 没有登录
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(NoLoginException.class)
    public Object noLoginExceptionHandler(HttpServletRequest request,HttpServletResponse response,Exception e)
    {
        log.error("[GlobalExceptionHandler][noLoginExceptionHandler] exception",e);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(JsonResultCode.NO_LOGIN);
        jsonResult.setMessage("用户登录失效或者登录超时,请先登录");
        return jsonResult;
    }

    /**
     *  业务异常
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    public Object businessExceptionHandler(HttpServletRequest request,HttpServletResponse response,Exception e)
    {
        log.error("[GlobalExceptionHandler][businessExceptionHandler] exception",e);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(JsonResultCode.FAILURE);
        jsonResult.setMessage("业务异常,请联系管理员");
        return jsonResult;
    }

    /**
     * 全局异常处理
     * @param request
     * @param response
     * @param e
     * @return
     */
	@ExceptionHandler(Exception.class)
	public Object exceptionHandler(HttpServletRequest request,HttpServletResponse response,Exception e)
    {
        log.error("[GlobalExceptionHandler][exceptionHandler] exception",e);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(JsonResultCode.FAILURE);
        jsonResult.setMessage("系统错误,请联系管理员");
        return jsonResult;
	}
}