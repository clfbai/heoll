package com.boyu.erp.platform.usercenter.controller.purchase;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PscDtlService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsxDtlService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.purchase.PscDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsxDtlVo;
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
 * @description: 购销申请明细
 * @author: wz
 * @create: 2019-7-19 16:12
 */
@Slf4j
@RestController
@RequestMapping("/purchase/psxDtl")
public class PurchasePsxDtlController extends BaseController {
    private static final String psxDtlCon = "购销申请明细";
    @Autowired
    private PsxDtlService psxDtlService;

    /**
     * 购销申请明细查询
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult psxDtlList(@RequestBody PsxDtlVo vo){
        try
        {
            Map<String,Object> resultMap=new HashMap<String,Object>();
            List<PsxDtlVo> list = psxDtlService.findByPsxNum(vo);
            resultMap.put("list",list);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultMap);
        } catch (ServiceException ex)
        {
            log.error("[PurchasePsxDtlController][psxDtlList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSX_DTL_R, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePsxDtlController][psxDtlList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSX_DTL_R, psxDtlCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加明细
     * @return
     */
    @RequestMapping(value = "/addPsxDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addPsxDtl(HttpServletRequest re,@RequestBody PsxDtlVo vo){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, psxDtlService.insertPsxDtl(vo));

        } catch (ServiceException ex)
        {
            log.error("[PurchasePsxDtlController][addPsxDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSX_DTL_C, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePsxDtlController][addPsxDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSX_DTL_C, psxDtlCon+CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改明细
     * @return
     */
    @RequestMapping(value = "/updatePsxDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatePsxDtl(HttpServletRequest re,@RequestBody PsxDtlVo vo){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS,psxDtlService.updatePsxDtl(vo));

        } catch (ServiceException ex)
        {
            log.error("[PurchasePsxDtlController][updatePsxDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSX_DTL_U, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePsxDtlController][updatePsxDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSX_DTL_U, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 删除明细
     * @return
     */
    @RequestMapping(value = "/deletePsxDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletePsxDtl(HttpServletRequest re,@RequestBody PsxDtlVo vo){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS,psxDtlService.deletePsxDtl(vo));

        } catch (ServiceException ex)
        {
            log.error("[PurchasePsxDtlController][deletePsxDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSX_DTL_D, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePsxDtlController][deletePsxDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSX_DTL_D, CommonFainl.DELETEFANS, "");
        }
    }



}



