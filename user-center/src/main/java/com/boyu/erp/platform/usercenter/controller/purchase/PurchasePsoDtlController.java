package com.boyu.erp.platform.usercenter.controller.purchase;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.purchase.PsoDtl;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsoDtlService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.purchase.PscDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsoDtlVo;
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
 * @description: 采购单明细
 * @author: wz
 * @create: 2019-7-18 11:32
 */
@Slf4j
@RestController
@RequestMapping("/purchase/psoDtl")
public class PurchasePsoDtlController extends BaseController {
    private static final String psoDtlCon = "采购单明细";
    @Autowired
    private PsoDtlService psoDtlService;

    /**
     * 通过购销单号进行购销明细查询
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult psoDtlList(@RequestBody PsoDtlVo vo){
        try
        {
            Map<String,Object> resultMap=new HashMap<String,Object>();

            List<PsoDtlVo> list = psoDtlService.findByPuoNum(vo);

            resultMap.put("list",list);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultMap);
        } catch (ServiceException ex)
        {
            log.error("[PurchasePsoDtlController][psoDtlList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSO_DTL_R, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePsoDtlController][psoDtlList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSO_DTL_R, psoDtlCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加明细
     * @return
     */
    @RequestMapping(value = "/addPsoDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addPsoDtl(@RequestBody PsoDtlVo vo){
        try
        {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS,psoDtlService.insertPsoDtl(vo));

        } catch (ServiceException ex)
        {
            log.error("[PurchasePsoDtlController][addPsoDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSO_DTL_C, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePsoDtlController][addPsoDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSO_DTL_C, psoDtlCon+CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改明细
     * @return
     */
    @RequestMapping(value = "/updatePsoDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatePsoDtl(@RequestBody PsoDtlVo vo){
        try
        {

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS,psoDtlService.updatePsoDtl(vo));

        } catch (ServiceException ex)
        {
            log.error("[PurchasePsoDtlController][updatePsoDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSO_DTL_U, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePsoDtlController][updatePsoDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSO_DTL_U, psoDtlCon+CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除明细
     * @return
     */
    @RequestMapping(value = "/deletePsoDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletePscDtl(@RequestBody PsoDtlVo vo){
        try
        {

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS,psoDtlService.deletePsoDtl(vo));

        } catch (ServiceException ex)
        {
            log.error("[PurchasePsoDtlController][deletePscDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSO_DTL_D, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePsoDtlController][deletePscDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSO_DTL_D, psoDtlCon+CommonFainl.DELETEFANS, "");
        }
    }

}



