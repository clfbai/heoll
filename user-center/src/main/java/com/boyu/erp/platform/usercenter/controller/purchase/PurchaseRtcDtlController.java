package com.boyu.erp.platform.usercenter.controller.purchase;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.mapper.purchase.RtcDtlMapper;
import com.boyu.erp.platform.usercenter.model.purchase.PscDtlModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsoDtlService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.RtcDtlService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.purchase.PsoDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcDtlVoByBatch;
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
 * @description: 退购合同明细
 * @author: wz
 * @create: 2019-7-26 9:50
 */
@Slf4j
@RestController
@RequestMapping("/purchase/rtcDtl")
public class PurchaseRtcDtlController extends BaseController {
    private static final String rtcDtlCon = "退购合同明细";
    @Autowired
    private PsoDtlService psoDtlService;
    @Autowired
    private RtcDtlService rtcDtlService;

    /**
     * 通过购销单号进行购销明细查询
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult psoDtlList(@RequestBody RtcDtlVo vo){
        try
        {
            Map<String,Object> resultMap=new HashMap<String,Object>();

            List<RtcDtlVo> list = rtcDtlService.findByRtcNum(vo);

            resultMap.put("list",list);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultMap);
        } catch (ServiceException ex)
        {
            log.error("[PurchasePsoDtlController][psoDtlList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTC_DTL_R, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePsoDtlController][psoDtlList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTC_DTL_R, rtcDtlCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加明细
     * @return
     */
    @RequestMapping(value = "/addRtcDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addRtcDtl(@RequestBody RtcDtlVo vo){
        try
        {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS,rtcDtlService.insertRtcDtl(vo));

        } catch (ServiceException ex)
        {
            log.error("[PurchasePsoDtlController][addPsoDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTC_DTL_C, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePsoDtlController][addPsoDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTC_DTL_C, rtcDtlCon+CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改明细
     * @return
     */
    @RequestMapping(value = "/updateRtcDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateRtcDtl(@RequestBody RtcDtlVo vo){
        try
        {

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS,rtcDtlService.updateRtcDtl(vo));

        } catch (ServiceException ex)
        {
            log.error("[PurchasePsoDtlController][updatePsoDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTC_DTL_U, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePsoDtlController][updatePsoDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTC_DTL_U, rtcDtlCon+CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除明细
     * @return
     */
    @RequestMapping(value = "/deleteRtcDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteRtcDtl(@RequestBody RtcDtlVo vo){
        try
        {

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS,rtcDtlService.deleteRtcDtl(vo));

        } catch (ServiceException ex)
        {
            log.error("[PurchasePsoDtlController][deletePscDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTC_DTL_D, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePsoDtlController][deletePscDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTC_DTL_D, rtcDtlCon+CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 通过退购明细中输入的明细，更新单价等等信息
     */
    @RequestMapping(value = "/updatePrcDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatePrcDtl(@RequestBody RtcDtlVoByBatch vo){
        try
        {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, rtcDtlService.updatePrcDtl(vo));
        } catch (ServiceException ex)
        {
            log.error("[PurchasePsoDtlController][updatePrcDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePsoDtlController][updatePrcDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "采购合同"+rtcDtlCon+CommonFainl.UPDATEFANS, "");
        }
    }


}



