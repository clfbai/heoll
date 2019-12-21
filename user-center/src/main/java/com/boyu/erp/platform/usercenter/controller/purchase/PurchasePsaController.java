package com.boyu.erp.platform.usercenter.controller.purchase;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.common.PrivIdExamine;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsaService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PscTypeService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.RtcTypeService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.system.SysUnitService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.common.OperationAuthorityUtils;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.FieldUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.UnitOptionVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsaVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PsxVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
@RequestMapping("/purchase/psa")
public class PurchasePsaController extends BaseController {
    private static final String psaCon = "采购协议";
    //判断权限操作字段
    private final String tableName = "psa|";
    private static final String Operations = "PurchasePsaController|";
    @Autowired
    private PsaService psaService;

    @Autowired
    private SysUnitService sysUnitService;

    @Autowired
    private SysCodeDtlService sysCodeDtlService;

    @Autowired
    private PscTypeService pscTypeService;

    @Autowired
    private RtcTypeService rtcTypeService;

    @Autowired
    private FieldUtils fieldUtils;

    @Autowired
    private ProdClsUtils prodClsUtils;

    @Autowired
    private OperationAuthorityUtils privUtils;


    /**
     * 采购协议查询
     *
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult psaList(@RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "15") Integer size,
                              HttpServletRequest re, PsaVo psa) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);

            PageInfo<PsaVo> resultList = psaService.selectVdeAll(psa, page, size, sysUser);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex) {
            log.error("[PsachasePsaController][psaList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PsachasePsaController][psaList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_R, psaCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加采购协议
     *
     * @param psaVo
     * @return
     */
    @SystemLog(name = "添加采购协议")
    @RequestMapping(value = "/addPsa", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addPsa(HttpServletRequest re, @RequestBody PsaVo psaVo) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);

            //判断是否有权限操作
            /*PrivIdExamine examine = privUtils.isUnitPriv(tableName, Operations, CommonFainl.ADD, psaVo.getUnitId(), user);
            if (!examine.getPrivBoolean()) {
                return new JsonResult(JsonResultCode.FAILURE, examine.getMeages(), "");
            }*/
            psaVo.setVendeeId(psaVo.getsUnitId()+"");
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, psaService.insertPsa(psaVo,user));
        } catch (ServiceException ex) {
            log.error("[PsachasePsaController][addPsa] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PsachasePsaController][addPsa] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_C, psaCon + CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改采购协议
     *
     * @param psaVo
     * @return
     */
    @SystemLog(name = "修改采购协议")
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
            log.error("[PsachasePsaController][updatePsa] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PsachasePsaController][updatePsa] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_U, psaCon + CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除采购协议
     *
     * @param psaVo
     * @return
     */
    @SystemLog(name = "删除采购协议")
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
            log.error("[PsachasePsaController][deletePsa] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PsachasePsaController][deletePsa] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_D, psaCon + CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * @return 组织弹窗
     */
    @RequestMapping(value = "/unitOption", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult unitOption(UnitOptionVo vo, @RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "15") Integer size,
                                 HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            PageInfo<UnitOptionVo> list = null;
            //判断是供应商弹窗  如果不是就要判断是否已选择供应商  否则传空集合
            if (StringUtils.isNotEmpty(vo.getUnitType())) {
                list = sysUnitService.selectByOption(vo, page, size, sysUser);
            } else {
                list = new PageInfo<>();
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, list);
        } catch (Exception ex) {
            log.error("[PsachasePsaController][unitOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSA_POPUP, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 下拉框
     *
     * @return
     */
    @RequestMapping(value = "/option", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult option() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();

            map.put("rtnCtrl", sysCodeDtlService.optionGet("RTN_CTRL"));//退货控制
            map.put("dfltPscType", pscTypeService.optionGet()); //缺省购销合同
            map.put("dfltRtcType", rtcTypeService.optionGet());//缺省退货合同
            map.put("dfltIsCms", sysCodeDtlService.optionGet("BRDG_MODE"));  //缺省代销
            map.put("authMode", sysCodeDtlService.optionGet("AUTH_MODE"));//授权方式
            map.put("manMode", sysCodeDtlService.optionGet("MAN_MODE"));//经营方式
            map.put("psRole", sysCodeDtlService.optionGet("PS_ROLE"));//协议控制方
            map.put("pscType", pscTypeService.optionGet()); //缺省购销合同
            map.put("inte", sysCodeDtlService.optionGet("IS_INTE"));//是否介入
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception ex) {
            log.error("[PsachasePsaController][option] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 查询采购协议必填字段
     *
     * @return
     */
    @RequestMapping(value = "/getPsaFile", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getFile() {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("PSA"));
        } catch (Exception ex) {
            log.error("[PsachasePsaController][getFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
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
            if (vo.getPsaCtlr().equals("E")) {
                update = 1;
            }
            map.put("update",update);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (ServiceException ex) {
            log.error("[PsachasePsaController][isUpdateOrDelete] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PsachasePsaController][isUpdateOrDelete] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUC_R, psaCon+CommonFainl.SELECTFIANL, "");
        }
    }

}



