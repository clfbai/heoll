package com.boyu.erp.platform.usercenter.controller.product;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.goods.ProdAttrDef;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.goods.ProdAttrDefModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.ProdAttrDefService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品属性定义
 */
@Slf4j
@RestController
@RequestMapping(value = "/product")
public class ProductAttrDefController extends BaseController {

    @Autowired
    private ProdAttrDefService prodAttrDefService;


    @Autowired
    private SysCodeDtlService codeDtlService;
    @Autowired
    private ProdClsUtils prodClsUtils;

    /**
     * 查询商品属性定义
     *
     * @return
     */
    @RequestMapping(value = "/prodAttrDef/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getProdAttrDefAll(@RequestParam(defaultValue = "0") Integer page,
                                        @RequestParam(defaultValue = "15") Integer size,
                                        ProdAttrDef prodAttrDef) {
        try {
            PageInfo<ProdAttrDef> prodAttrDefPageInfo = prodAttrDefService.selectAll(page, size, prodAttrDef);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", prodAttrDefPageInfo);
        } catch (ServiceException ex) {
            log.error("[ProductAttrDefController][getProdAttrDefAll] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductAttrDefController][getProdAttrDefAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }


    /**
     * 添加商品属性定义
     *
     * @return
     */
    @SystemLog(name = "添加商品属性定义")
    @RequestMapping(value = "/add/prodAttrDef", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addProdAttrDef(@RequestBody ProdAttrDefModel prodAttrDef, HttpServletRequest request) {
        try {
            int result = prodAttrDefService.insertProdAttrDef(prodAttrDef, this.getSessionSysUser(request));
            if (result >= 1) {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员", "");
            }
        } catch (ServiceException ex) {
            log.error("[ProductAttrDefController][addProdAttrDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductAttrDefController][addProdAttrDef] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 修改商品属性定义
     *
     * @return
     */
    @SystemLog(name = "修改商品属性定义")
    @RequestMapping(value = "/update/prodAttrDef", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateProdAttrDef(@RequestBody ProdAttrDefModel prodAttrDefModel, HttpServletRequest request) {
        try {
            int result = prodAttrDefService.updateProdAttrDef(prodAttrDefModel, this.getSessionSysUser(request));
            if (result >= 1) {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功", result);
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员", "");
            }
        } catch (ServiceException ex) {
            log.error("[ProductAttrDefController][updateProdAttrDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductAttrDefController][updateProdAttrDef] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 删除商品属性定义
     *
     * @return
     */
    @SystemLog(name = "删除商品属性定义")
    @RequestMapping(value = "/delete/prodAttrDef", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteProdAttrDef(@RequestBody ProdAttrDefModel prodAttrDef, HttpServletRequest request) {
        try {
            int result = prodAttrDefService.deleteProdAttrDef(prodAttrDef, this.getSessionSysUser(request));

            if (result >= 1) {
                return new JsonResult(JsonResultCode.SUCCESS, "删除成功", "");
            } else {
                return new JsonResult(JsonResultCode.FAILURE, "删除成功，请联系管理员", "");
            }
        } catch (ServiceException ex) {
            log.error("[ProductAttrDefController][deleteProdAttrDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductAttrDefController][deleteProdAttrDef] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 查询所有自定义属性 属性类别对应属性名称
     *
     * @return
     */
    @RequestMapping(value = "/get/AttrAndName", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult AttrAndName(@RequestBody ProdAttrDef prodAttrDef) {
        try {

            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", prodAttrDefService.getAttrAndName());

        } catch (ServiceException ex) {
            log.error("[ProductAttrDefController][deleteProdAttrDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductAttrDefController][AttrAndName] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

    /**
     * 商品属性定义数据类型下拉  ---是否必需
     *
     * @return
     */
    @RequestMapping(value = "/prodAttrDef/isNeedOptions", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult isNeedOptions() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();
            map.put("isNeed", codeDtlService.optionGet("TandF"));
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", map);
        } catch (ServiceException ex) {
            log.error("[ProductAttrDefController][isNeedOptions] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductAttrDefController][isNeedOptions] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }

    /**
     * 商品属性定义数据类型下拉  ---是否大小写
     *
     * @return
     */
    @RequestMapping(value = "/prodAttrDef/charCaseOptions", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult charCaseOptions() {
        try {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("charCase", codeDtlService.optionGet("CHAR_CASE"));
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", resultMap);
        } catch (ServiceException ex) {
            log.error("[ProductAttrDefController][charCaseOptions] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductAttrDefController][charCaseOptions] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }


    /**
     * 商品属性定义数据类型下拉  ---数据类型
     *
     * @return
     */
    @RequestMapping(value = "/prodAttrDef/dataTypeOptions", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult prodAttrDefOptions() {
        try {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("dataType", codeDtlService.optionGet("DATA_TYPE"));
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", resultMap);
        } catch (
                ServiceException ex) {
            log.error("[ProductAttrDefController][prodAttrDefOptions] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ProductAttrDefController][prodAttrDefOptions] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败", "");
        }
    }
}