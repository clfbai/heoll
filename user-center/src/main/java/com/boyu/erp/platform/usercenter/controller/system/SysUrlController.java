package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUrl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysUrlSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: boyu-platform
 * @description: 路径控制层
 * @author: clf
 * @create: 2019-05-31 11:56
 */
@Slf4j
@RestController
@RequestMapping("/user/url")
public class SysUrlController extends BaseController {
    @Autowired
    private SysUrlSerivce urlSerivce;

    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getUrlList(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "15") Integer size,
                                 SysUrl url,
                                 HttpServletRequest request) {
        try {
            this.isNullUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", urlSerivce.pageUrl(page, size, url));
        } catch (ServiceException ex) {
            log.error("[SysUrlSerivce][getUrlList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SysUrlController][getUrlList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    @SystemLog(name = "新增URL")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addUrl(@RequestBody SysUrl url, HttpServletRequest request) {
        try {
            if (urlSerivce.findUrl(url)) {
                return new JsonResult(JsonResultCode.SUCCESS, "增加成功", urlSerivce.addUrl(url, (SysUser) this.isNullUser(request)));
            }
            return new JsonResult(JsonResultCode.FAILURE, "路径Id,或者路径,或者路径描述已存在", "");
        } catch (ServiceException ex) {
            log.error("[SysUrlSerivce][addUrl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SysUrlController][addUrl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    @SystemLog(name = "修改URL")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateUrl(@RequestBody SysUrl url, HttpServletRequest request) {
        try {
            SysUrl us = new SysUrl();
            us.setUrl(url.getUrl());
            if (!urlSerivce.findUrl(us)) {
                return new JsonResult(JsonResultCode.FAILURE, "路径已存在", "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, "修改成功", urlSerivce.updateUrl(url, (SysUser) this.isNullUser(request)));
        } catch (ServiceException ex) {
            log.error("[SysUrlSerivce][ updateUrl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SysUrlController][ updateUrl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    @SystemLog(name = "删除URL")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteUrl(@RequestBody SysUrl url, HttpServletRequest request) {
        try {

            return new JsonResult(JsonResultCode.SUCCESS, "删除成功", urlSerivce.deleteUrl(url, (SysUser) this.isNullUser(request)));
        } catch (ServiceException ex) {
            log.error("[SysUrlSerivce][ updateUrl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SysUrlController][ updateUrl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }
}
