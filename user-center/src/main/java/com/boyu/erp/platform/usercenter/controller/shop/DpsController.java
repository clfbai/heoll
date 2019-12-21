package com.boyu.erp.platform.usercenter.controller.shop;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.shop.Dps;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.shop.DpsServicer;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/shop/dps")
public class DpsController extends BaseController {
    private static final String table = "dps|";

    private static final String Operations = "DpsController|";
    @Autowired
    private DpsServicer dpsServicer;
    @Autowired
    private SysCodeDtlService codeDtlService;
    @Autowired
    private OperationAuthorityUtils operationAuthorityUtils;

    @GetMapping(value = "/list")
    public JsonResult dpsList(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                              @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                              Dps dps, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, dpsServicer.getDps(page, size, dps, this.getSessionSysUser(request)));
        } catch (ServiceException ex) {
            log.error("[ShopAttrDefServicer][getDps] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopAttrDefController][dpsList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询商场信息失败:DpsController.dpsList(),Exception Null", "");
        }
    }


    @PostMapping(value = "/add")
    @SystemLog(name = "增加商场")
    public JsonResult addDps(@RequestBody Dps dps, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            PrivIdExamine examine = operationAuthorityUtils.isUnitPriv(table, Operations, CommonFainl.ADD, dps.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, "增加商场成功", dpsServicer.addDps(dps));
        } catch (ServiceException ex) {
            log.error("[ShopAttrDefServicer][addDps] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopAttrDefController][addDps] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "增加商场信息失败:DpsController.addDpsList(),Exception Null", "");
        }
    }


    @PostMapping(value = "/update")
    @SystemLog(name = "修改商场")
    public JsonResult updateDps(@RequestBody Dps dps, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            PrivIdExamine examine = operationAuthorityUtils.isUnitPriv(table, Operations, CommonFainl.UPDATE, dps.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, "修改商场信息成功", dpsServicer.updateDps(dps));
        } catch (ServiceException ex) {
            log.error("[ShopAttrDefServicer][updateDps] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopAttrDefController][updateDps] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "修改商场信息失败:DpsController.updateDps(),Exception Null", "");
        }
    }

    @PostMapping(value = "/delete")
    @SystemLog(name = "删除商场")
    public JsonResult deleteDps(@RequestBody Dps dps, HttpServletRequest request) {
        try {
            SysUser user = this.getSessionSysUser(request);
            PrivIdExamine examine = operationAuthorityUtils.isUnitPriv(table, Operations, CommonFainl.DELETE, dps.getOwnerId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }
            return new JsonResult(JsonResultCode.SUCCESS, "删除商场信息成功", dpsServicer.deleteDps(dps));
        } catch (ServiceException ex) {
            log.error("[ShopAttrDefServicer][deleteDps] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ShopAttrDefController][deleteDps] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "删除商场信息失败:DpsController.deleteDps(),Exception Null", "");
        }
    }


    @PostMapping(value = "/options")
    public JsonResult optionsDps() {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("dpsLvl", codeDtlService.optionGet("DPS_LVL"));
            map.put("cptrGthr", codeDtlService.optionGet("TandF"));
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception ex) {
            log.error("[ShopAttrDefController][optionsDps] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询商场下拉失败:DpsController.optionsDps(),Exception Null", "");
        }
    }
}
