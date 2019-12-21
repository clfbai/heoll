package com.boyu.erp.platform.product.controller;

import com.boyu.erp.platform.product.result.JsonResult;
import com.boyu.erp.platform.product.result.JsonResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/workflow")
public class UsersController extends BaseController
{
    /**
     * 我的待办
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/my/todo",method = {RequestMethod.GET,RequestMethod.POST})
    public JsonResult myTodo(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            return new JsonResult(JsonResultCode.SUCCESS,"查询成功","");
        }catch (Exception ex)
        {
            log.error("[UsersController][myTodo]Exception",ex);
            return new JsonResult(JsonResultCode.FAILURE,"系统错误,请联系管理员","");
        }
    }
}
