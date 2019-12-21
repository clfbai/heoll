package com.boyu.erp.platform.usercenter.controller.sales;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaService;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.purchase.PsaVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * @program: boyu-platform
 * @description: 采购协议控制层
 * @author: wz
 * @create: 2019-06-21 10:19
 */
@Slf4j
@RestController
@RequestMapping("/sales/psa")
public class SalesPsaController extends BaseController {
    private static final String psaCon = "销售协议";
    //判断权限操作字段
    private final String tableName = "psa|";
    private static final String Operations = "SalesPsaController|";
    @Autowired
    private PsaService psaService;

    @Autowired
    private OperationAuthorityUtils privUtils;


    /**
     * 销售协议查询
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult psaList(@RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "15") Integer size,
                              HttpServletRequest re, PsaVo psa) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);

            PageInfo<PsaVo> resultList = psaService.selectVdrAll(psa, page, size, sysUser);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex) {
            log.error("[SalesPsaController][psaList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesPsaController][psaList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_R, psaCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加销售协议
     *
     * @param psaVo
     * @return
     */
    @SystemLog(name = "添加销售协议")
    @RequestMapping(value = "/addPsa", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addPsa(HttpServletRequest re, @RequestBody PsaVo psaVo) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);

            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.ADD, psaVo.getUnitId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            psaVo.setVenderId(psaVo.getsUnitId()+"");
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, psaService.insertPsa(psaVo,user));
        } catch (ServiceException ex) {
            log.error("[SalesPsaController][addPsa] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesPsaController][addPsa] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_C, psaCon + CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改采购协议
     *
     * @param psaVo
     * @return
     */
    @SystemLog(name = "修改销售协议")
    @RequestMapping(value = "/updatePsa", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatePsa(HttpServletRequest re, @RequestBody PsaVo psaVo) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);

            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.UPDATE, psaVo.getUnitId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, psaService.updatePsa(psaVo,user));
        } catch (ServiceException ex) {
            log.error("[SalesPsaController][updatePsa] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesPsaController][updatePsa] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_U, psaCon + CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除采购协议
     *
     * @param psaVo
     * @return
     */
    @SystemLog(name = "删除销售协议")
    @RequestMapping(value = "/deletePsa", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletePsa(HttpServletRequest re, @RequestBody PsaVo psaVo) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);

            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.DELETE, psaVo.getUnitId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, psaService.deletePsa(psaVo));
        } catch (ServiceException ex) {
            log.error("[SalesPsaController][deletePsa] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesPsaController][deletePsa] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_D, psaCon + CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 判断是否可以修改
     * @param vo
     * @return
     */
    @SystemLog(name = "判断是否可以修改")
    @RequestMapping(value = "/isUpdateOrDelete", method = {RequestMethod.POST})
    public JsonResult isUpdateOrDelete(@RequestBody PsaVo vo) {
        try {
            Map<String, Object> map = new HashMap<>();
            int update = 0;
            if (vo.getPsaCtlr().equals("R")) {
                update = 1;
            }
            map.put("update",update);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[SalesPsaController][isUpdateOrDelete] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesPsaController][isUpdateOrDelete] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, psaCon+CommonFainl.SELECTFIANL, "");
        }
    }

}



