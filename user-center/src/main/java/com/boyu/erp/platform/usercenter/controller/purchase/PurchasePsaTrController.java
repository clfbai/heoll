package com.boyu.erp.platform.usercenter.controller.purchase;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaTrService;
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
 * @description: 税率
 * @author: wz
 * @create: 2019-07-02 11:57
 */
@Slf4j
@RestController
@RequestMapping("/purchase/psaTr")
public class PurchasePsaTrController extends BaseController {
    private static final String psaTrCon = "税率";
    @Autowired
    private PsaTrService psaTrService;
    @Autowired
    private PurchasePsaUtils psaUtils;


    /**
     * 查询税率
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult psaTrList(@RequestBody PsaRateVo vo){
        try
        {
            Map<String,Object> resultMap=new HashMap<String,Object>();
            List<PsaRateVo> resultList = psaTrService.selectAll(vo);
            resultMap.put("list",resultList);
            //不可见字段
            List<PsaFieldVo> field = psaUtils.full("TR");
            resultMap.put("fieldFull",field);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultMap);
        } catch (ServiceException ex)
        {
            log.error("[PurchasePsaTrController][psaTrList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_TR_R, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePsaTrController][psaTrList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_TR_R, psaTrCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加税率
     * @param vo
     * @return
     */
    @SystemLog(name = "添加税率")
    @RequestMapping(value = "/addPsaTr", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addPsaTr(@RequestBody PsaRateVo vo){
        try
        {

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS,psaTrService.insertByPsa(vo));

        }catch (ServiceException ex)
        {
            log.error("[PurchasePsaTrController][addPsaTr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_TR_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePsaTrController][addPsaTr] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_TR_C, psaTrCon+ CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改税率
     * @param vo
     * @return
     */
    @SystemLog(name = "修改税率")
    @RequestMapping(value = "/updatePsaTr", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatePsaTr(@RequestBody PsaRateVo vo){
        try
        {

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS,psaTrService.updateByPsa(vo));

        }catch (ServiceException ex)
        {
            log.error("[PurchasePsaTrController][updatePsaTr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_TR_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePsaTrController][updatePsaTr] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_TR_U, psaTrCon+ CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除税率
     * @param vo
     * @return
     */
    @SystemLog(name = "删除税率")
    @RequestMapping(value = "/deletePsaTr", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletePsaTr(@RequestBody PsaRateVo vo){
        try
        {

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS,psaTrService.deleteByPsa(vo));

        }catch (ServiceException ex)
        {
            log.error("[PurchasePsaTrController][deletePsaTr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_TR_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePsaTrController][deletePsaTr] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_TR_D, psaTrCon+ CommonFainl.DELETEFANS, "");
        }
    }

}



