package com.boyu.erp.platform.usercenter.controller.purchase;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.purchase.PsxType;
import com.boyu.erp.platform.usercenter.entity.purchase.PuaType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsxTypeService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PuaTypeService;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.vo.purchase.PuaTypeVo;
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
 * @description: 采购申请控制层
 * @author: wz
 * @create: 2019-06-18 14:15
 */
@Slf4j
@RestController
@RequestMapping("/purchase/puaType")
public class PurchasePuaTypeController extends BaseController {
    @Autowired
    private PuaTypeService puaTypeService;
    @Autowired
    private PsxTypeService psxTypeService;
    @Autowired
    private ProdClsUtils prodClsUtils;


    /**
     * 采购申请类别
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult puaTypeList(@RequestParam(defaultValue = "0") Integer page,
                                @RequestParam(defaultValue = "15") Integer size,
                                  PuaTypeVo vo){
        try
        {
            PageInfo<PuaTypeVo> resultList = puaTypeService.selectAll(page, size, vo);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", resultList);
        } catch (ServiceException ex)
        {
            log.error("[PurchasePuaTypeController][puaTypeList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePuaTypeController][puaTypeList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 添加采购申请类别
     * @param vo
     * @return
     */
    @SystemLog(name = "添加采购申请类别")
    @RequestMapping(value = "/addPuaType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addPuaType(@RequestBody PuaTypeVo vo, HttpServletRequest re){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);

            return new JsonResult(JsonResultCode.SUCCESS, "操作成功",puaTypeService.insertPuaType(vo,sysUser));

        }catch (ServiceException ex)
        {
            log.error("[PurchasePuaTypeController][addPuaType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuaTypeController][addPuaType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 修改采购申请类别
     * @param vo
     * @return
     */
    @SystemLog(name = "修改采购申请类别")
    @RequestMapping(value = "/updatePuaType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatePuaType(@RequestBody PuaTypeVo vo, HttpServletRequest re){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);

            return new JsonResult(JsonResultCode.SUCCESS, "操作成功",puaTypeService.updatePuaType(vo,sysUser));

        }catch (ServiceException ex)
        {
            log.error("[PurchasePuaTypeController][updatePuaType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuaTypeController][updatePuaType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 删除采购申请类别
     * @param vo
     * @return
     */
    @SystemLog(name = "删除采购申请类别")
    @RequestMapping(value = "/deletePuaType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletePuaType(@RequestBody PuaTypeVo vo, HttpServletRequest re){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功",puaTypeService.deletePuaType(vo,sysUser));

        } catch (ServiceException ex)
        {
            log.error("[PurchasePuaTypeController][deletePuaType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePuaTypeController][deletePuaType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 采购申请类别中购销意向类别下拉框
     *
     * @return
     */
    @RequestMapping(value = "/option", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult psiTypeOption() {
        try {
            List<PsxType> list = psxTypeService.optionGet();
            List<PurKeyValue> keys = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            if (list.size() > 0) {
                for (PsxType m : list) {
                    PurKeyValue ms = new PurKeyValue( m.getDescription(),m.getPsxType());
                    keys.add(ms);
                }
            }
            map.put("active", prodClsUtils.getList());
            map.put("psxType", keys);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", map);
        } catch (ServiceException ex) {
            log.error("[PurchaseVdrAttrDefController][psiTypeOption] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "购销申请类别下拉服务异常");
        } catch (Exception ex) {
            log.error("[PurchaseVdrAttrDefController][psiTypeOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "查询失败失败", "");
        }
    }


}



