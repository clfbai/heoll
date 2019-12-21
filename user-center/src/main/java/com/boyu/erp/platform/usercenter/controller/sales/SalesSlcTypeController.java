package com.boyu.erp.platform.usercenter.controller.sales;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.sales.SlcType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PscTypeService;
import com.boyu.erp.platform.usercenter.service.salesservice.SlcTypeService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 销售合同控制层
 * @author: ck
 * @create: 2019-06-21 11:43
 */
@Slf4j
@RestController
@RequestMapping(value = "/sales")
public class SalesSlcTypeController extends BaseController {
    @Autowired
    private SlcTypeService slcTypeService;

    @Autowired
    private PscTypeService pscTypeService;

    @Autowired
    private ProdClsUtils prodClsUtils;

    @Autowired
    private SysCodeDtlService sysCodeDtlService;

    /**
     * 查询所有销售合同(支持模糊查询)
     * @return
     */
    @RequestMapping(value = "/slcType/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getSlcTypeList(@RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "15") Integer size,
                                     HttpServletRequest re,
                                     SlcType slcType) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            PageInfo<SlcType> pageInfo = slcTypeService.getSlcTypeList(page, size, slcType);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", pageInfo);
        } catch (ServiceException ex) {
            log.error("[SlcTypeService][getSlcTypeList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SlcTypeService][getSlcTypeList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询异常", "");
        }
    }



    /**
     * 创建销售合同
     */
    @SystemLog(name = "创建销售合同类别")
    @RequestMapping(value = "/slcType/createSlcType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult creatSlcType(@RequestBody SlcType slcType, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", slcTypeService.insert(slcType,user));
        } catch (ServiceException ex) {
            log.error("[SlcTypeService][creatSlcType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SlcTypeService][creatSlcType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }

    }

    /**
     * 更新销售合同
     */
    @SystemLog(name = "更新销售合同类别")
    @RequestMapping(value = "/slcType/updateSlcType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateSlcType(@RequestBody SlcType slcType, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", slcTypeService.updateBySlcType(slcType,user));
        } catch (ServiceException ex) {
            log.error("[SlcTypeService][updateSlcType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SlcTypeService][updateSlcType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }

    /**
     * 删除销售合同
     */
    @SystemLog(name = "删除销售合同类别")
    @RequestMapping(value = "/slcType/deleteSlcType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteSlcType(@RequestBody SlcType slcType, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", slcTypeService.deleteBySlcType(slcType,user));
        } catch (ServiceException ex) {
            log.error("[SlcTypeService][deleteSlcType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SlcTypeService][deleteSlcType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }

    /**
     * 获取销售合同类别下拉框
     *
     * @return
     */
    @RequestMapping(value = "/slcType/option", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult psiTypeOption() {
        try {
            List<PurKeyValue> list = slcTypeService.optionGet();
            Map<String, Object> map = new HashMap<>();
            map.put("list", list);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", map);
        } catch (ServiceException ex) {
            log.error("[PurchaseVdrAttrDefController][psiTypeOption] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "购销意向类别下拉服务异常");
        } catch (Exception ex) {
            log.error("[PurchaseVdrAttrDefController][psiTypeOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败失败", "");
        }
    }

    @RequestMapping(value = "/slcType/getSlcTypeOption", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getSlcTypeOption() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();
            map.put("pscType", pscTypeService.optionGet());//购销合同
            map.put("slBrdgMode", sysCodeDtlService.optionGet("BRDG_MODE"));
            map.put("slBrdgModeAlt", prodClsUtils.getList());
            map.put("afReqd", prodClsUtils.getList());
            map.put("afReqdAlt", prodClsUtils.getList());
            map.put("active", prodClsUtils.getList());

            return new JsonResult(JsonResultCode.SUCCESS, "获取信息成功", map);
        } catch (Exception ex) {
            log.error("[UserController][getUserOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "获取信息失败", "");
        }
    }
}
