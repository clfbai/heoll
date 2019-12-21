package com.boyu.erp.platform.usercenter.controller.sales;

import com.boyu.erp.platform.usercenter.components.FieldAuthority;
import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.basic.Vendee;
import com.boyu.erp.platform.usercenter.entity.mq.MessageObject;
import com.boyu.erp.platform.usercenter.entity.system.SysUnitClsf;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.system.table.TableFile;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.model.sales.VdeAttrModel;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PurchaseService;
import com.boyu.erp.platform.usercenter.service.salesservice.VdeAttrDefService;
import com.boyu.erp.platform.usercenter.service.salesservice.VdeAttrService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.system.VendeeService;
import com.boyu.erp.platform.usercenter.utils.StringUtils;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.FieldUtils;
import com.boyu.erp.platform.usercenter.utils.system.SysUnitUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.partner.VenVo;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcVo;
import com.boyu.erp.platform.usercenter.vo.purchase.VenderVo;
import com.boyu.erp.platform.usercenter.vo.sales.VendeeVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Classname SalesVendeeController
 * @Description TODO
 * @Date 2019/9/2 9:11
 * @Created by wz
 */
@Slf4j
@RestController
@RequestMapping("/sales/vendee")
public class SalesVendeeController extends BaseController {

    private static final String vendeeCon = "采购商";

    @Autowired
    private VendeeService vendeeService;
    @Autowired
    private VdeAttrService vdeAttrService;
    @Autowired
    private VdeAttrDefService vdeAttrDefService;
    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private SysCodeDtlService sysCodeDtlService;
    @Autowired
    private FieldUtils fieldUtils;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private SysUnitUtils unitUtils;

    /**
     * 供应商查询
     *
     * @return
     */
    @FieldAuthority(name = "VENDEE")
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public Object vendeeList(
            VendeeVo vo, @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "15") Integer size,
            HttpServletRequest re
    ) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if(StringUtils.NullEmpty(vo.getVdeStatus())){
                vo.setVdeStatus("A");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, vendeeService.selectAll(page, size, vo, sysUser));

        } catch (ServiceException ex) {
            log.error("[SalesVendeeController][vendeeList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesVendeeController][vendeeList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_R, vendeeCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加采购商
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/addVendee", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addVendee(HttpServletRequest re, @RequestBody VendeeVo vo) {
        try {
            SysUser user = this.getSessionSysUser(re);
            if (org.apache.commons.lang3.StringUtils.isNotBlank(vo.getUnitCode()) || org.apache.commons.lang3.StringUtils.isNotBlank(vo.getUnitName())) {
                if (unitUtils.isCodeAndName(vo.getUnitName(), vo.getUnitCode(), CommonFainl.ADD)) {
                    return new JsonResult(JsonResultCode.FAILURE_SYS_UNIT, vendeeCon + CommonFainl.ADDFIANL + "： 组织代码或组织名称已存在", "");
                }
            }

            vo.setOwnerId(vo.getsUnitId());
            //将当前组织放入对象中
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, vendeeService.insertVendee(vo, user));

        } catch (ServiceException ex) {
            log.error("[SalesVendeeController][addVendee] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesVendeeController][addVendee] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_C, vendeeCon + CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改采购商
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/updateVendee", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateVendee(HttpServletRequest re, @RequestBody VendeeVo vo) {
        try {
            SysUser user = this.getSessionSysUser(re);
            if (org.apache.commons.lang3.StringUtils.isNotBlank(vo.getUnitCode()) || org.apache.commons.lang3.StringUtils.isNotBlank(vo.getUnitName())) {
                if (unitUtils.isCodeAndName(vo.getUnitName(), vo.getUnitCode(), CommonFainl.UPDATE)) {
                    return new JsonResult(JsonResultCode.FAILURE_SYS_UNIT, vendeeCon + CommonFainl.UPDATEFANS + "： 组织代码或组织名称已存在", "");
                }
            }

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, vendeeService.updateVendee(vo, user));

        } catch (ServiceException ex) {
            log.error("[SalesVendeeController][updateVendee] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesVendeeController][updateVendee] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_U, vendeeCon + CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除供应商
     *
     * @param vo
     * @return
     */
    @SystemLog(name = "删除供应商")
    @RequestMapping(value = "/deleteVendee", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteVendee(HttpServletRequest re, @RequestBody VendeeVo vo) {
        try {
            SysUser user = this.getSessionSysUser(re);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, vendeeService.deleteVendee(vo, user));

        } catch (ServiceException ex) {
            log.error("[SalesVendeeController][deleteVendee] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesVendeeController][deleteVendee] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_D, vendeeCon + CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 自定义属性查询
     */
    @SystemLog(name = "自定义属性查询")
    @RequestMapping(value = "/vdeAttrList", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult vdeAttrList(@RequestBody Vendee ven) {
        try {
            List<Map<String, Object>> list = vdeAttrService.vdeAttrList(ven);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, list);
        } catch (ServiceException ex) {
            log.error("[SalesVendeeController][vdeAttrList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_VDR_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesVendeeController][vdeAttrList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_VDR_R, vendeeCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * @return 自定义属性
     */
    @RequestMapping(value = "/AttrAndName", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult AttrAndName() {
        try {

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, vdeAttrDefService.findByAll());
        } catch (ServiceException ex) {
            log.error("[SalesVendeeController][AttrAndName] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesVendeeController][AttrAndName] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, vendeeCon + CommonFainl.SELECTFIANL);
        }
    }

    /**
     * 修改采购商自定义属性
     */
    @SystemLog(name = "修改采购商自定义属性")
    @RequestMapping(value = "/updateVdeAttr", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateVdeAttr(@RequestBody VdeAttrModel vdeAttrModel) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, vdeAttrService.updateVdeAttr(vdeAttrModel));
        } catch (ServiceException ex) {
            log.error("[SalesVendeeController][updateVdeAttr] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_VDR_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesVendeeController][updateVdeAttr] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_VDR_U, vendeeCon + CommonFainl.UPDATEFANS, "");
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

            map.put("licType", sysCodeDtlService.optionGet("LIC_TYPE"));//执照类别
            map.put("recruitable", prodClsUtils.getList());//可征募
            map.put("shared", sysCodeDtlService.optionGet("BOOLEA"));//是否共享
            map.put("unitStatus", sysCodeDtlService.optionGet("UNIT_STATUS"));//状态
            map.put("pcrLvl", sysCodeDtlService.optionGet("PCR_LVL"));//信用等级
            map.put("dfltDelivMthd", sysCodeDtlService.optionGet("DELIV_MTHD"));//缺省送货方式
            map.put("ptnrStatus", sysCodeDtlService.optionGet("PTNR_STATUS"));//伙伴状态
            map.put("psaCtlr", sysCodeDtlService.optionGet("PS_ROLE"));//协议控制方
            map.put("vdeStatus", sysCodeDtlService.optionGet("VDE_STATUS"));//采购商状态
            map.put("sdPlcyId", new ArrayList<PurKeyValue>());//供需策略

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception ex) {
            log.error("[SalesVendeeController][option] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, vendeeCon + CommonFainl.SELECTFIANL);
        }
    }

    /**
     * 根据所选择或者所输入的供应商商代码  属主为当前组织id
     */
    @RequestMapping(value = "/vendeeByCode", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult vendeeByCode(HttpServletRequest re, @RequestBody VendeeVo vo) {
        try {
            VendeeVo vos = vendeeService.selectByCode(vo);
            //为空将传过来的对象返回
            if(vos==null){
                vos = vo;
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, vos);
        } catch (ServiceException ex) {
            log.error("[SalesVendeeController][vendeeByCode] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_UNIT_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesVendeeController][vendeeByCode] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_UNIT_U, vendeeCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 查询供应商必填字段
     *
     * @return
     */
    @RequestMapping(value = "/getVendeeFile", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getVendeeFile() {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("VENDEE,SYS_UNIT,COMPANY,PARTNER,SYS_UNIT_OWNER"));
        } catch (Exception ex) {
            log.error("[SalesVendeeController][getVendeeFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 冻结采购商
     *
     * @param re
     * @param vo
     * @return
     */
    @RequestMapping(value = "/freeze", method = {RequestMethod.POST})
    public JsonResult freeze(HttpServletRequest re, @RequestBody VendeeVo vo) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.FUNCTIONOPSTUS, vendeeService.freeze(vo));
        } catch (ServiceException ex) {
            log.error("[SalesVendeeController][freeze] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_UNIT_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesVendeeController][freeze] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_UNIT_U, vendeeCon + CommonFainl.FUNCTIONOPFANS, "");
        }
    }

    /**
     * 解冻采购商
     *
     * @param re
     * @param vo
     * @return
     */
    @RequestMapping(value = "/defreeze", method = {RequestMethod.POST})
    public JsonResult defreeze(HttpServletRequest re, @RequestBody VendeeVo vo) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.FUNCTIONOPSTUS, vendeeService.defreeze(vo));
        } catch (ServiceException ex) {
            log.error("[SalesVendeeController][defreeze] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_UNIT_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesVendeeController][defreeze] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_VENDER_UNIT_U, vendeeCon + CommonFainl.FUNCTIONOPFANS, "");
        }
    }

    /**
     * 采购商可操作状态
     *
     * @return
     */
    @RequestMapping(value = "/vdeAuditType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult vdeAuditType(@RequestBody SysUnitClsf sys, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            if (sysUser == null) {
                return new JsonResult(JsonResultCode.FAILURE, "请登录", "请登录");
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, vendeeService.initButtons(sys));
        } catch (ServiceException ex) {
            log.error("[SalesVendeeController][vdeAuditType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesVendeeController][vdeAuditType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, vendeeCon + CommonFainl.SELECTFIANL, "");
        }
    }

    @RequestMapping(value = "/updateNotFile", method = {RequestMethod.POST})
    public JsonResult updateNotFile(HttpServletRequest re,@RequestBody VenVo vo) {
        try {
            SysUser user = this.getSessionSysUser(re);
            List<TableFile> at = null;
            if(!org.springframework.util.StringUtils.isEmpty(vo.getCtrlUnitId())) {
                if (!vo.getCtrlUnitId().equals(user.getUnit().getUnitId() + "")) {
                    at = purchaseService.updateNotFile("VENDEE", vo.getMethoyType());
                }
            }
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, at);
        } catch (ServiceException ex) {
            log.error("[SalesVendeeController][updateNotFile] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesVendeeController][updateNotFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }


}
