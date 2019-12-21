package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUnitPa;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysUnitPaService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/user/unitpa")
public class UnitPaController extends BaseController {

    @Autowired
    private SysUnitPaService unitPaService;

    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult list(@RequestParam(defaultValue = "0") Integer page,
                           @RequestParam(defaultValue = "15") Integer size,
                           SysUnitPa unitPa, HttpServletRequest request) {
        try {
            this.isNullUser(request);
            PageInfo<SysUnitPa> pageInfo = unitPaService.selectAll(page, size, unitPa);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", pageInfo);
        } catch (ServiceException ex) {
            log.error("[TAreaServcie][getTArea] exception", ex);
            return new JsonResult("600800", ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UnitPaController][listgoods] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

    @SystemLog(name = "修改组织权限分配表 ")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateUnitPa(@RequestBody SysUnitPa unitPa, HttpServletRequest request) {
        try {
            this.isNullUser(request);
            unitPaService.updateByPrimaryKeySelective(unitPa);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
        } catch (ServiceException ex) {
            log.error("[TAreaServcie][getTArea] exception", ex);
            return new JsonResult("600800", ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[UnitPaController][updateUnitPa] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

}
