package com.boyu.erp.platform.usercenter.controller.purchase;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaDrService;
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
 * @description: 折率
 * @author: wz
 * @create: 2019-07-02 11:57
 */
@Slf4j
@RestController
@RequestMapping("/purchase/psaDr")
public class PurchasePsaDrController extends BaseController {
    private static final String psaDrCon = "折率";
    @Autowired
    private PsaDrService psaDrService;
    @Autowired
    private PurchasePsaUtils psaUtils;

    /**
     * 折率
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult psaDrList(@RequestBody PsaRateVo vo){
        try
        {
            Map<String,Object> resultMap=new HashMap<String,Object>();
            List<PsaRateVo> resultList = psaDrService.selectAll(vo);
            resultMap.put("list",resultList);
            //不可见字段
            List<PsaFieldVo> field = psaUtils.full("DR");
            resultMap.put("fieldFull",field);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultMap);
        } catch (ServiceException ex)
        {
            log.error("[PurchasePsaDrController][psaDrList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_DR_R, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePsaDrController][psaDrList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_DR_R, psaDrCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加折率
     * @param vo
     * @return
     */
    @SystemLog(name = "添加折率")
    @RequestMapping(value = "/addPsaDr", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addPsaDr(@RequestBody PsaRateVo vo){
        try
        {

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS,psaDrService.insertByPsa(vo));

        }catch (ServiceException ex)
        {
            log.error("[PurchasePsaDrController][addPsaDr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_DR_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePsaDrController][addPsaDr] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_DR_C, psaDrCon+ CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改折率
     * @param vo
     * @return
     */
    @SystemLog(name = "修改折率")
    @RequestMapping(value = "/updatePsaDr", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatePsaDr(@RequestBody PsaRateVo vo){
        try
        {

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS,psaDrService.updateByPsa(vo));

        }catch (ServiceException ex)
        {
            log.error("[PurchasePsaDrController][updatePsaDr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_DR_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePsaDrController][updatePsaDr] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_DR_U, psaDrCon+ CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除折率
     * @param vo
     * @return
     */
    @SystemLog(name = "删除折率")
    @RequestMapping(value = "/deletePsaDr", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletePsaDr(@RequestBody PsaRateVo vo){
        try
        {

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS,psaDrService.deleteByPsa(vo));

        }catch (ServiceException ex)
        {
            log.error("[PurchasePsaDrController][deletePsaDr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_DR_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePsaDrController][deletePsaDr] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_DR_D, psaDrCon+ CommonFainl.DELETEFANS, "");
        }
    }

}



