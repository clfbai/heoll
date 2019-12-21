package com.boyu.erp.platform.usercenter.controller.price;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.priceservice.PpnScpService;
import com.boyu.erp.platform.usercenter.service.salesservice.SpnScpService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.price.PpnScpVo;
import com.boyu.erp.platform.usercenter.vo.price.SpnScpVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname SpnScpController
 * @Description TODO
 * @Date 2019/8/26 9:39
 * @Created by wz
 */
@Slf4j
@RestController
@RequestMapping("/price/spnScp")
public class SpnScpController extends BaseController {
    private static final String spnScpCon = "销售价格单范围";

    @Autowired
    private SpnScpService spnScpService;

    /**
     * 采购价格单查询
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult spnScpList(@RequestBody SpnScpVo vo, @RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "15") Integer size,
                                 HttpServletRequest re) {
        try {

            Map<String,Object> resultMap=new HashMap<String,Object>();

            List<SpnScpVo> list = spnScpService.findBySpn(vo);

            resultMap.put("list",list);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultMap);
        } catch (ServiceException ex) {
            log.error("[SpnScpController][spnScpList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNSCP_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnScpController][spnScpList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNSCP_R, spnScpCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加采购价格单范围
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "添加采购价格单范围")
    @RequestMapping(value = "/addSpnScp", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addSpnScp(HttpServletRequest re, @RequestBody SpnScpVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, spnScpService.insertSpnScp(vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[SpnScpController][addSpnScp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNSCP_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnScpController][addSpnScp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNSCP_C, spnScpCon+CommonFainl.ADDFIANL, "");
        }
    }


    /**
     * 删除销售价格单范围
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "删除销售价格单范围")
    @RequestMapping(value = "/deleteSpnScp", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteSpnScp(HttpServletRequest re, @RequestBody SpnScpVo vo) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, spnScpService.deleteSpnScp(vo, sysUser));


        } catch (ServiceException ex) {
            log.error("[SpnScpController][deleteSpnScp] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNSCP_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SpnScpController][deleteSpnScp] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PPNSCP_D, spnScpCon+CommonFainl.DELETEFANS, "");
        }
    }

}
