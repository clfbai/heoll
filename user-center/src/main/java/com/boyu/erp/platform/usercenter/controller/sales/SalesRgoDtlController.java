package com.boyu.erp.platform.usercenter.controller.sales;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.sales.RgoDtl;
import com.boyu.erp.platform.usercenter.entity.sales.RgoType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.salesservice.RgoDtlService;
import com.boyu.erp.platform.usercenter.service.salesservice.RgoService;
import com.boyu.erp.platform.usercenter.service.salesservice.RgoTypeService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.FieldUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsoDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcDtlVo;
import com.boyu.erp.platform.usercenter.vo.purchase.RtcDtlVoByBatch;
import com.boyu.erp.platform.usercenter.vo.sales.RgoDtlVo;
import com.boyu.erp.platform.usercenter.vo.sales.RgoProdDtlVo;
import com.boyu.erp.platform.usercenter.vo.sales.RgoVo;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: SalesRgoDtlController
 * @description: 调配单明细控制层
 * @author: wz
 * @create: 2019-10-7 11:16
 */
@Slf4j
@RestController
@RequestMapping(value = "/sales/rgoDtl")
public class SalesRgoDtlController extends BaseController {
    private static final String rgoDtlCon = "调配单明细";

    @Autowired
    private RgoDtlService rgoDtlService;

    /**
     * 通过调配单号进行调配明细查询
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult rgoDtlList(@RequestBody RgoDtlVo vo){
        try
        {
            Map<String,Object> resultMap=new HashMap<String,Object>();

            List<RgoDtlVo> list = rgoDtlService.selectByRgoDtl(vo);

            resultMap.put("list",list);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultMap);
        } catch (ServiceException ex)
        {
            log.error("[SalesRgoDtlController][rgoDtlList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSO_DTL_R, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[SalesRgoDtlController][rgoDtlList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSO_DTL_R, rgoDtlCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加明细
     * @return
     */
    @RequestMapping(value = "/addRgoDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addRgoDtl(@RequestBody RgoDtlVo vo){
        try
        {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS,rgoDtlService.insertRgoDtl(vo));

        } catch (ServiceException ex)
        {
            log.error("[SalesRgoDtlController][addRgoDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSO_DTL_C, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[SalesRgoDtlController][addRgoDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSO_DTL_C, rgoDtlCon+CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改明细
     * @return
     */
    @RequestMapping(value = "/updateRgoDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateRgoDtl(@RequestBody RgoDtlVo vo){
        try
        {

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS,rgoDtlService.updateRgoDtl(vo));

        } catch (ServiceException ex)
        {
            log.error("[SalesRgoDtlController][updateRgoDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTC_DTL_U, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[SalesRgoDtlController][updateRgoDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTC_DTL_U, rgoDtlCon+CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除明细
     * @return
     */
    @RequestMapping(value = "/deleteRgoDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteRgoDtl(@RequestBody RgoDtlVo vo){
        try
        {

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS,rgoDtlService.deleteRgoDtl(vo));

        } catch (ServiceException ex)
        {
            log.error("[SalesRgoDtlController][deleteRgoDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTC_DTL_D, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[SalesRgoDtlController][deleteRgoDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTC_DTL_D, rgoDtlCon+CommonFainl.DELETEFANS, "");
        }
    }

    @RequestMapping(value = "/updateRgoProdDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateRgoProdDtl(@RequestBody RgoProdDtlVo vo){
        try
        {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, rgoDtlService.updateDtl(vo));
        } catch (ServiceException ex)
        {
            log.error("[SalesRgoDtlController][updateRgoProdDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[SalesRgoDtlController][updateRgoProdDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "采购合同"+rgoDtlCon+CommonFainl.UPDATEFANS, "");
        }
    }

}
