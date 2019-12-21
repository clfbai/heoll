package com.boyu.erp.platform.usercenter.controller.price;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.priceservice.PpnDtlService;
import com.boyu.erp.platform.usercenter.service.priceservice.SpnDtlService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.price.PpnDtlVo;
import com.boyu.erp.platform.usercenter.vo.price.SpnDtlVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname SpnDtlController
 * @Description TODO
 * @Date 2019/8/26 9:39
 * @Created by wz
 */
@Slf4j
@RestController
@RequestMapping("/price/spnDtl")
public class SpnDtlController extends BaseController {
    private static final String ppnDtlCon = "销售价格单明细";

    @Autowired
    private SpnDtlService spnDtlService;

    /**
     * 销售价格单查询
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult spnDtlList(@RequestBody SpnDtlVo vo, @RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "15") Integer size,
                                 HttpServletRequest re) {
        try {

            Map<String,Object> resultMap=new HashMap<String,Object>();

            List<SpnDtlVo> list = spnDtlService.findBySpn(vo);

            resultMap.put("list",list);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultMap);
        } catch (ServiceException ex) {
            log.error("[SpnDtlController][spnDtlList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNDTL_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnDtlController][spnDtlList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNDTL_R, ppnDtlCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加销售价格单明细
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "添加销售价格单明细")
    @RequestMapping(value = "/addSpnDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addSpnDtl(HttpServletRequest re, @RequestBody SpnDtlVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, spnDtlService.insertSpnDtl(vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[SpnDtlController][addSpnDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNDTL_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnDtlController][addSpnDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNDTL_C, ppnDtlCon+CommonFainl.ADDFIANL, "");
        }
    }


    /**
     * 修改销售价格单明细
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "修改销售价格单明细")
    @RequestMapping(value = "/updateSpnDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateSpnDtl(HttpServletRequest re, @RequestBody SpnDtlVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, spnDtlService.updateSpnDtl(vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[SpnDtlController][updateSpnDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNDTL_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnDtlController][updateSpnDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNDTL_U, ppnDtlCon+CommonFainl.UPDATEFANS, "");
        }
    }


    /**
     * 删除销售价格单明细
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "删除销售价格单明细")
    @RequestMapping(value = "/deleteSpnDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteSpnDtl(HttpServletRequest re, @RequestBody SpnDtlVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, spnDtlService.deleteSpnDtl(vo, sysUser));


        } catch (ServiceException ex) {
            log.error("[SpnDtlController][deleteSpnDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNDTL_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnDtlController][deleteSpnDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNDTL_D, ppnDtlCon+CommonFainl.DELETEFANS, "");
        }
    }

}
