package com.boyu.erp.platform.usercenter.controller.purchase;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaRtrService;
import com.boyu.erp.platform.usercenter.utils.purchase.PurchasePsaUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.purchase.PsaFieldVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsaRateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: boyu-platform
 * @description: 可退率
 * @author: wz
 * @create: 2019-07-02 11:57
 */
@Slf4j
@RestController
@RequestMapping("/purchase/psaRtr")
public class PurchasePsaRtrController extends BaseController {
    private static final String psaRtrCon = "可退率";
    @Autowired
    private PsaRtrService psaRtrService;
    @Autowired
    private PurchasePsaUtils psaUtils;


    /**
     * 查询可退率
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult psaRtrList(@RequestBody PsaRateVo vo){
        try
        {
            Map<String,Object> resultMap=new HashMap<String,Object>();
            List<PsaRateVo> resultList = psaRtrService.selectAll(vo);
            resultMap.put("list",resultList);
            //不可见字段
            List<PsaFieldVo> field = psaUtils.full("RTR");
            resultMap.put("fieldFull",field);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultMap);
        } catch (ServiceException ex)
        {
            log.error("[PurchasePsaRtrController][psaRtrList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_RTR_R, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePsaRtrController][psaRtrList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_RTR_R, psaRtrCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加可退率
     * @param vo
     * @return
     */
    @SystemLog(name = "添加折率")
    @RequestMapping(value = "/addPsaRtr", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addPsaRtr(@RequestBody PsaRateVo vo){
        try
        {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS,psaRtrService.insertByPsa(vo));
        }catch (ServiceException ex)
        {
            log.error("[PurchasePsaRtrController][addPsaRtr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_RTR_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePsaRtrController][addPsaRtr] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_RTR_C, psaRtrCon+ CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改可退率
     * @param vo
     * @return
     */
    @SystemLog(name = "修改折率")
    @RequestMapping(value = "/updatePsaRtr", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatePsaRtr(@RequestBody PsaRateVo vo){
        try
        {

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS,psaRtrService.updateByPsa(vo));

        }catch (ServiceException ex)
        {
            log.error("[PurchasePsaRtrController][updatePsaRtr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_RTR_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePsaRtrController][updatePsaRtr] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_RTR_U, psaRtrCon+ CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除可退率
     * @param vo
     * @return
     */
    @SystemLog(name = "删除折率")
    @RequestMapping(value = "/deletePsaRtr", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletePsaRtr(@RequestBody PsaRateVo vo){
        try
        {

            return new JsonResult(JsonResultCode.SUCCESS, "操作成功",psaRtrService.deleteByPsa(vo));

        }catch (ServiceException ex)
        {
            log.error("[PurchasePsaRtrController][deletePsaRtr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_RTR_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePsaRtrController][deletePsaRtr] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_RTR_D, psaRtrCon+ CommonFainl.DELETEFANS, "");
        }
    }

}



