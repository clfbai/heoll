package com.boyu.erp.platform.usercenter.exception;

import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局异常
 *
 * @author Administrator
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 没有登录
     *
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(NoLoginException.class)
    public Object noLoginExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        log.error("[GlobalExceptionHandler][noLoginExceptionHandler] exception", e);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(JsonResultCode.NO_LOGIN);
        jsonResult.setMessage("用户登录失效或者登录超时,请先登录");
        return jsonResult;
    }


    /**
     * 服务层异常
     *
     * @param request
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    public Object serviceExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        log.error("[GlobalExceptionHandler][serviceExceptionHandler] exception", e);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(JsonResultCode.FAILURE);
        jsonResult.setMessage("服务异常");
        return jsonResult;
    }


    /**
     * 全局异常处理
     *
     * @param request
     * @param response
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Object exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        log.error("[GlobalExceptionHandler][exceptionHandler] ", ex);
        //全局参数校验
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exs = (MethodArgumentNotValidException) ex;
            //捕获的所有错误对象
            ObjectError allErrors = exs.getBindingResult().getAllErrors().get(0);
            String s = allErrors.getDefaultMessage();
            //错误信息
            log.info(s);
            //错误字段
            String sl = allErrors.getCodes()[0];
            return new JsonResult(JsonResultCode.FAILURE, s, sl);
        }
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(JsonResultCode.FAILURE);
        jsonResult.setMessage("系统错误,请联系管理员");
        return jsonResult;
    }

    //处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常，详情继续往下看代码
    @ExceptionHandler(BindException.class)
    public Object BindExceptionHandler(BindException e) {
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return new JsonResult(JsonResultCode.FAILURE,message,"");
    }

    //处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    public Object ConstraintViolationExceptionHandler(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        return new JsonResult(JsonResultCode.FAILURE,message,"");
    }

    //处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        String[] sh=e.getBindingResult().getAllErrors().get(0).getCodes();
        return new JsonResult(JsonResultCode.FAILURE,message,sh[0]);
    }

}