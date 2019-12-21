package com.boyu.erp.platform.usercenter.controller.sales;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.sales.SrcType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.RtcTypeService;
import com.boyu.erp.platform.usercenter.service.salesservice.SrcTypeService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
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
 * @description: 退销合同类别合同控制层
 * @author: ck
 * @create: 2019-06-21 11:43
 */
@Slf4j
@RestController
@RequestMapping(value = "/sales")
public class SalesSrcTypeController extends BaseController {
    @Autowired
    private SrcTypeService srcTypeService;

    @Autowired
    private RtcTypeService rtcTypeService;

    @Autowired
    private ProdClsUtils prodClsUtils;

    @Autowired
    private SysCodeDtlService sysCodeDtlService;

    /**
     * 查询所有销售合同(支持模糊查询)
     * @return
     */
    @RequestMapping(value = "/srcType/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getSrcTypeList(@RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "15") Integer size,
                                     HttpServletRequest re,
                                     SrcType srcType) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            PageInfo<SrcType> pageInfo = srcTypeService.getSrcTypeList(page, size, srcType);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", pageInfo);
        } catch (ServiceException ex) {
            log.error("[srcTypeService][getSrcTypeList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[srcTypeService][getSrcTypeList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询异常", "");
        }
    }



    /**
     * 创建退销合同
     */
    @SystemLog(name = "创建退销合同")
    @RequestMapping(value = "/srcType/createSrcType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult creatSrcType(@RequestBody SrcType srcType, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", srcTypeService.insert(srcType,user));
        } catch (ServiceException ex) {
            log.error("[srcTypeService][creatSrcType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[srcTypeService][creatSrcType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }

    }

    /**
     * 更新退销合同
     */
    @SystemLog(name = "更新退销合同")
    @RequestMapping(value = "/srcType/updateSrcType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateSrcType(@RequestBody SrcType srcType, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", srcTypeService.updateBySrcType(srcType,user));
        } catch (ServiceException ex) {
            log.error("[srcTypeService][updateSrcType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[srcTypeService][updateSrcType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }

    /**
     * 删除退销合同
     */
    @SystemLog(name = "更新退销合同")
    @RequestMapping(value = "/srcType/deleteSrcType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteSrcType(@RequestBody SrcType srcType, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", srcTypeService.deleteBySrcType(srcType,user));
        } catch (ServiceException ex) {
            log.error("[srcTypeService][deleteSrcType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[srcTypeService][deleteSrcType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }
    /**
     * 获取销售合同类别下拉框
     *
     * @return
     */
    @RequestMapping(value = "/srcType/option", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult psiTypeOption() {
        try {
            List<SrcType> list = srcTypeService.optionGet();
            List<PurKeyValue> keys = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            if (list.size() > 0) {
                for (SrcType m : list) {
                    PurKeyValue ms = new PurKeyValue(m.getSrcType(), m.getDescription());
                    keys.add(ms);
                }
            }
            map.put("list", keys);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", map);
        } catch (ServiceException ex) {
            log.error("[PurchaseVdrAttrDefController][psiTypeOption] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "购销意向类别下拉服务异常");
        } catch (Exception ex) {
            log.error("[PurchaseVdrAttrDefController][psiTypeOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败失败", "");
        }
    }

    @RequestMapping(value = "/srcType/getSrcTypeOption", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getSlcTypeOption() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();
            map.put("rtcType", rtcTypeService.optionGet());//购销合同
            map.put("srBrdgMode", prodClsUtils.getList());
            map.put("srBrdgModeAlt", prodClsUtils.getList());
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
