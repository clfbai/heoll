package com.boyu.erp.platform.usercenter.controller.purchase;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PscDtlService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.purchase.PscDtlVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: boyu-platform
 * @description: 购销合同明细
 * @author: wz
 * @create: 2019-7-8 12:13
 */
@Slf4j
@RestController
@RequestMapping("/purchase/pscDtl")
public class PurchasePscDtlController extends BaseController {
    private static final String pscDtlCon = "采购合同明细";
    @Autowired
    private PscDtlService pscDtlService;

    /**
     * 通过购销合同号进行购销合同明细查询
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public JsonResult pscDtlList(@RequestBody PscDtlVo vo){
        try
        {
            Map<String,Object> resultMap=new HashMap<String,Object>();
            List<PscDtlVo> list = pscDtlService.findByPscNum(vo);
            resultMap.put("list",list);
//            if(vo.getProdId()!=null){
//                if(listgoods==null){
//                    return new JsonResult(JsonResultCode.FAILURE_PUC_PROD_R, CommonFainl.PRODfAIL, resultMap);
//                }
//            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultMap);
        } catch (ServiceException ex)
        {
            log.error("[PurchasePscDtlController][pscDtlList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSC_DTL_R, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePscDtlController][pscDtlList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSC_DTL_R, pscDtlCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加明细
     * @return
     */
    @RequestMapping(value = "/addPscDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addPscDtl(HttpServletRequest re,@RequestBody PscDtlVo vo){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS,pscDtlService.insertPscDtl(vo,sysUser));

        } catch (ServiceException ex)
        {
            log.error("[PurchasePscDtlController][addPscDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSC_DTL_C, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePscDtlController][addPscDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSC_DTL_C, pscDtlCon+CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改明细
     * @return
     */
    @RequestMapping(value = "/updatePscDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatePscDtl(HttpServletRequest re,@RequestBody PscDtlVo vo){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS,pscDtlService.updatePscDtl(vo,sysUser));

        } catch (ServiceException ex)
        {
            log.error("[PurchasePscDtlController][updatePscDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSC_DTL_U, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePscDtlController][updatePscDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSC_DTL_U, pscDtlCon+CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除明细
     * @return
     */
    @RequestMapping(value = "/deletePscDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletePscDtl(HttpServletRequest re,@RequestBody PscDtlVo vo){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS,pscDtlService.deletePscDtl(vo,sysUser));

        } catch (ServiceException ex)
        {
            log.error("[PurchasePscDtlController][deletePscDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSC_DTL_D, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePscDtlController][deletePscDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSC_DTL_D, pscDtlCon+CommonFainl.DELETEFANS, "");
        }
    }

}



