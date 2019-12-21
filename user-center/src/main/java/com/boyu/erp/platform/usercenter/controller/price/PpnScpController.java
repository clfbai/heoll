package com.boyu.erp.platform.usercenter.controller.price;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.priceservice.PpnDtlService;
import com.boyu.erp.platform.usercenter.service.priceservice.PpnScpService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.price.PpnDtlVo;
import com.boyu.erp.platform.usercenter.vo.price.PpnScpVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname PpnScpController
 * @Description TODO
 * @Date 2019/8/26 9:39
 * @Created by wz
 */
@Slf4j
@RestController
@RequestMapping("/price/ppnScp")
public class PpnScpController extends BaseController {
    private static final String ppnScpCon = "采购价格单范围";

    @Autowired
    private PpnScpService ppnScpService;

    /**
     * 采购价格单查询
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult ppnScpList(@RequestBody PpnScpVo vo, @RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "15") Integer size,
                                 HttpServletRequest re) {
        try {

            Map<String,Object> resultMap=new HashMap<String,Object>();

            List<PpnScpVo> list = ppnScpService.findByPpn(vo);

            resultMap.put("list",list);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultMap);
        } catch (ServiceException ex) {
            log.error("[PpnScpController][ppnScpList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNSCP_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnScpController][ppnScpList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNSCP_R, ppnScpCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加采购价格单范围
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "添加采购价格单范围")
    @RequestMapping(value = "/addPpnScp", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addPpnScp(HttpServletRequest re, @RequestBody PpnScpVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, ppnScpService.insertPpnScp(vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[PpnScpController][addPpnScp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNSCP_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnScpController][addPpnScp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNSCP_C, ppnScpCon+CommonFainl.ADDFIANL, "");
        }
    }


    /**
     * 删除采购价格单范围
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "删除采购价格单范围")
    @RequestMapping(value = "/deletePpnScp", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletePpnScp(HttpServletRequest re, @RequestBody PpnScpVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, ppnScpService.deletePpnScp(vo, sysUser));


        } catch (ServiceException ex) {
            log.error("[PpnScpController][deletePpnScp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNSCP_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PpnScpController][deletePpnScp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNSCP_D, ppnScpCon+CommonFainl.DELETEFANS, "");
        }
    }

}
