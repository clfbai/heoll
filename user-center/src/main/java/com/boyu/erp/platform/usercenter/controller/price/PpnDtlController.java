package com.boyu.erp.platform.usercenter.controller.price;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.priceservice.PpnDtlService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.price.PpnDtlVo;
import com.boyu.erp.platform.usercenter.vo.price.PpnVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname PricePurchase
 * @Description TODO
 * @Date 2019/8/26 9:39
 * @Created by wz
 */
@Slf4j
@RestController
@RequestMapping("/price/ppnDtl")
public class PpnDtlController extends BaseController {
    private static final String ppnDtlCon = "采购价格单明细";

    @Autowired
    private PpnDtlService ppnDtlService;

    /**
     * 采购价格单查询
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult ppnDtlList(@RequestBody PpnDtlVo vo, @RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "15") Integer size,
                              HttpServletRequest re) {
        try {

            Map<String,Object> resultMap=new HashMap<String,Object>();

            List<PpnDtlVo> list = ppnDtlService.findByPpn(vo);

            resultMap.put("list",list);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultMap);
        } catch (ServiceException ex) {
            log.error("[PpnDtlController][ppnDtlList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNDTL_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnDtlController][ppnDtlList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNDTL_R, ppnDtlCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加采购价格单明细
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "添加采购价格单明细")
    @RequestMapping(value = "/addPpnDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addPpnDtl(HttpServletRequest re, @RequestBody PpnDtlVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, ppnDtlService.insertPpnDtl(vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[PpnDtlController][addPpnDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNDTL_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnDtlController][addPpnDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNDTL_C, ppnDtlCon+CommonFainl.ADDFIANL, "");
        }
    }


    /**
     * 修改采购价格单明细
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "修改采购价格单明细")
    @RequestMapping(value = "/updatePpnDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatePpnDtl(HttpServletRequest re, @RequestBody PpnDtlVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, ppnDtlService.updatePpnDtl(vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[PpnDtlController][updatePpn] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNDTL_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnDtlController][updatePpn] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNDTL_U, ppnDtlCon+CommonFainl.UPDATEFANS, "");
        }
    }


    /**
     * 删除采购价格单明细
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "删除采购价格单明细")
    @RequestMapping(value = "/deletePpnDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletePpn(HttpServletRequest re, @RequestBody PpnDtlVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, ppnDtlService.deletePpnDtl(vo, sysUser));


        } catch (ServiceException ex) {
            log.error("[PpnDtlController][deletePpnDtl] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNDTL_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnDtlController][deletePpnDtl] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNDTL_D, ppnDtlCon+CommonFainl.DELETEFANS, "");
        }
    }

}
