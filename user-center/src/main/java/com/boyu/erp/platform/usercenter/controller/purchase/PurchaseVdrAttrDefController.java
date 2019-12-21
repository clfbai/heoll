package com.boyu.erp.platform.usercenter.controller.purchase;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.purchase.VdrAttrDef;
import com.boyu.erp.platform.usercenter.entity.sales.SlaType;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;

import com.boyu.erp.platform.usercenter.service.purchaseservice.VdrAttrDefService;
import com.boyu.erp.platform.usercenter.utils.purchase.FieldUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @program: boyu-platform
 * @description:
 * @author: wz
 * @create: 2019-06-17 16:12
 */
@Slf4j
@RestController
@RequestMapping("/purchase/vdrAttrDef")
public class PurchaseVdrAttrDefController extends BaseController {
    @Autowired
    private VdrAttrDefService vdrAttrDefService;
    @Autowired
    private FieldUtils fieldUtils;

    /**
     * 供应商属性
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult vdrAttrDefList(@RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "15") Integer size,
                                     VdrAttrDef vdrAttrDef) {
        try {
            PageInfo<VdrAttrDef> resultList = vdrAttrDefService.selectAll(page, size, vdrAttrDef);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", resultList);
        } catch (ServiceException ex) {
            log.error("[PurchaseVdrAttrDefController][vdrAttrDefList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseVdrAttrDefController][vdrAttrDefList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 添加供应商属性
     *
     * @param vdrAttrDef
     * @return
     */
    @SystemLog(name = "添加供应商属性")
    @RequestMapping(value = "/addVdrAttrDef", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addVdrAttrDef(@RequestBody VdrAttrDef vdrAttrDef) {
        try {
            int result = vdrAttrDefService.insertVdrAttrDef(vdrAttrDef);

            if (result == 1) {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员", "");
            }
        } catch (ServiceException ex) {
            log.error("[PurchaseVdrAttrDefController][addVdrAttrDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseVdrAttrDefController][addVdrAttrDef] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 修改供应商属性
     *
     * @param vdrAttrDef
     * @return
     */
    @SystemLog(name = "修改供应商属性")
    @RequestMapping(value = "/updateVdrAttrDef", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateVdrAttrDef(@RequestBody VdrAttrDef vdrAttrDef) {
        try {
            int result = vdrAttrDefService.updateVdrAttrDef(vdrAttrDef);

            if (result == 1) {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员", "");
            }
        } catch (ServiceException ex) {
            log.error("[PurchaseVdrAttrDefController][updateVdrAttrDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseVdrAttrDefController][updateVdrAttrDef] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 删除供应商属性
     *
     * @param vdrAttrDef
     * @return
     */
    @SystemLog(name = "删除供应商属性")
    @RequestMapping(value = "/deleteVdrAttrDef", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteVdrAttrDef(@RequestBody VdrAttrDef vdrAttrDef) {
        try {
            int result = vdrAttrDefService.deleteVdrAttrDef(vdrAttrDef);

            if (result == 1) {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员", "");
            }
        } catch (ServiceException ex) {
            log.error("[PurchaseVdrAttrDefController][deleteVdrAttrDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchaseVdrAttrDefController][deleteVdrAttrDef] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 查询供应商属性定义必填字段
     *
     * @return
     */
    @RequestMapping(value = "/getVdrAttrDefFile", method = {RequestMethod.POST})
    public JsonResult getVdrAttrDefFile() {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("vdr_attr_def", "VDRATTRDEF"));
        } catch (Exception ex) {
            log.error("[PurchaseVdrAttrDefController][getVdrAttrDefFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }
}



