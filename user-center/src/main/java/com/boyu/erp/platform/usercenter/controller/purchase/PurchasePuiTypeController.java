package com.boyu.erp.platform.usercenter.controller.purchase;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.purchase.PsiType;
import com.boyu.erp.platform.usercenter.entity.purchase.PuiType;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsiTypeService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PuiTypeService;
import com.boyu.erp.platform.usercenter.vo.purchase.PuiTypeVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: boyu-platform
 * @description: 采购意向控制层
 * @author: wz
 * @create: 2019-06-18 14:15
 */
@Slf4j
@RestController
@RequestMapping("/purchase/puiType")
public class PurchasePuiTypeController extends BaseController {
    @Autowired
    private PuiTypeService puiTypeService;
    @Autowired
    private PsiTypeService psiTypeService;

    /**
     * 采购意向类别
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult puiTypeList(@RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "15") Integer size,
                                  @RequestBody PuiType puiType){
        try
        {
            PageInfo<PuiTypeVo> resultList = puiTypeService.selectAll(page, size, puiType);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", resultList);
        } catch (ServiceException ex)
        {
            log.error("[PurchasePuiTypeController][puiTypeList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePuiTypeController][puiTypeList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 添加采购意向类别
     * @param puiType
     * @return
     */
    @SystemLog(name = "添加采购意向类别")
    @RequestMapping(value = "/addPuiType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addPuiType(@RequestBody PuiType puiType){
        try
        {
            int result=puiTypeService.insertPuiType(puiType);

            if(result==1)
            {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功","");
            }else
            {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员","");
            }
        }catch (ServiceException ex)
        {
            log.error("[PurchasePuiTypeController][addPuiType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuiTypeController][addPuiType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 修改采购意向类别
     * @param puiType
     * @return
     */
    @SystemLog(name = "修改采购意向类别")
    @RequestMapping(value = "/updatePuiType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatePuiType(@RequestBody PuiType puiType){
        try
        {
            int result=puiTypeService.updatePuiType(puiType);

            if(result==1)
            {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功","");
            }else
            {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员","");
            }
        }catch (ServiceException ex)
        {
            log.error("[PurchasePuiTypeController][updatePuiType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePuiTypeController][updatePuiType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 删除采购意向类别
     * @param puiType
     * @return
     */
    @SystemLog(name = "删除采购意向类别")
    @RequestMapping(value = "/deletePuiType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletePuiType(@RequestBody PuiType puiType){
        try
        {
            int result=puiTypeService.deletePuiType(puiType);

            if(result==1)
            {
                return new JsonResult(JsonResultCode.SUCCESS, "操作成功","");
            }else
            {
                return new JsonResult(JsonResultCode.FAILURE, "操作失败，请联系管理员","");
            }
        } catch (ServiceException ex)
        {
            log.error("[PurchasePuiTypeController][deletePuiType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePuiTypeController][deletePuiType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 采购意向类别中购销意向类别下拉框
     *
     * @return
     */
    @RequestMapping(value = "/option", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult psiTypeOption() {
        try {
            List<PsiType> list = psiTypeService.optionGet();
            List<PurKeyValue> keys = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            if (list.size() > 0) {
                for (PsiType m : list) {
                    PurKeyValue ms = new PurKeyValue(m.getPsiType(), m.getDescription());
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


}



