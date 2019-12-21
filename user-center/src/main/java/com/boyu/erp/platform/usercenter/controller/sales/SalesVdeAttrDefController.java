package com.boyu.erp.platform.usercenter.controller.sales;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.sales.VdeAttrDef;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.salesservice.VdeAttrDefService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.FieldUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @program: boyu-platform
 * @description: 采购商属性定义控制层
 * @author: ck
 * @create: 2019-06-21 11:43
 */
@Slf4j
@RestController
@RequestMapping(value = "/sales")
public class SalesVdeAttrDefController extends BaseController {
    @Autowired
    private VdeAttrDefService vdeAttrDefService;

    @Autowired
    private SysCodeDtlService sysCodeDtlService;

    @Autowired
    private ProdClsUtils prodClsUtils;

    @Autowired
    private FieldUtils fieldUtils;

    /**
     * 查询所有采购商属性定义
     *
     * @return
     */
    @RequestMapping(value = "/vdeAttrDef/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult vdeAttrDefList(@RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "15") Integer size,
                                     HttpServletRequest re,
                                     VdeAttrDef vdeAttrDef) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            PageInfo<VdeAttrDef> pageInfo = vdeAttrDefService.getAllVdeAttrDefList(page, size, vdeAttrDef);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", pageInfo);
        } catch (ServiceException ex) {
            log.error("[VdeAttrDefService][getAllVdeAttrDefList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[VdeAttrDefService][getAllVdeAttrDefList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询异常", "");
        }
    }


    /**
     * 创建采购商属性定义
     */
    @SystemLog(name = "创建采购商属性定义")
    @RequestMapping(value = "/vdeAttrDef/createVdeAttrDef", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult creatVdeAttrDef(@RequestBody VdeAttrDef vdeAttrDef) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", vdeAttrDefService.insert(vdeAttrDef));
        } catch (ServiceException ex) {
            log.error("[VdeAttrDefService][creatVdeAttrDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[VdeAttrDefService][creatVdeAttrDef] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }

    }

    /**
     * 更新采购商属性定义
     */
    @SystemLog(name = "更新采购商属性定义")
    @RequestMapping(value = "/vdeAttrDef/updateVdeAttrDef", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateVdeAttrDef(@RequestBody VdeAttrDef vdeAttrDef) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", vdeAttrDefService.updateByAttrType(vdeAttrDef));
        } catch (ServiceException ex) {
            log.error("[VdeAttrDefService][updateVdeAttrDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[VdeAttrDefService][updateVdeAttrDef] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }

    /**
     * 删除采购商属性定义
     */
    @SystemLog(name = "删除采购商属性定义")
    @RequestMapping(value = "/vdeAttrDef/deleteVdeAttrDef", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteVdeAttrDef(@RequestBody VdeAttrDef vdeAttrDef) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", vdeAttrDefService.deleteByAttrType(vdeAttrDef));
        } catch (ServiceException ex) {
            log.error("[VdeAttrDefService][deleteVdeAttrDef] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[VdeAttrDefService][deleteVdeAttrDef] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }

    /**
     * 查询采购商属性定义必填字段
     *
     * @return
     */
    @RequestMapping(value = "/vdeAttrDef/getVdeAttrDefFile", method = {RequestMethod.POST})
    public JsonResult getVdeAttrDefFile() {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("vde_attr_def", "VDEATTRDEF"));
        } catch (Exception ex) {
            log.error("[VdeAttrDefService][getVdeAttrDefFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 返回数据类型，大小写，是否必需
     *
     * @return
     */
    @RequestMapping(value = "/vdeAttrDef/getVdeAttrDefOption", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getVdeAttrDefOption() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();
            /**
             * 获取数据类型option
             */
            map.put("dataType", sysCodeDtlService.optionGet("DATA_TYPE"));
            /**
             * 获取大小写option
             */
            map.put("charCase", sysCodeDtlService.optionGet("CHAR_CASE"));
            map.put("valReq", prodClsUtils.getList());
            map.put("isOpetion", prodClsUtils.getList());

            return new JsonResult(JsonResultCode.SUCCESS, "获取信息成功", map);
        } catch (Exception ex) {
            log.error("[VdeAttrDefService][getVdeAttrDefOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "获取信息失败", "");
        }
    }
}
