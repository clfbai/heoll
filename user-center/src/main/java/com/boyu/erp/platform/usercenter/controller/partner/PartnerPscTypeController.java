package com.boyu.erp.platform.usercenter.controller.partner;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.purchase.PscType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PscTypeService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.utils.warehouse.DelivUtil;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.partner.PscTypeVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * @program: boyu-platform
 * @description: 购销合同控制层
 * @author: wz
 * @create: 2019-8-21 10:15
 */
@Slf4j
@RestController
@RequestMapping("/partner/pscType")
public class PartnerPscTypeController extends BaseController {
    private static final String pscTypeCon = "购销合同类别";

    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private PscTypeService pscTypeService;
    @Autowired
    private SysCodeDtlService sysCodeDtlService;
    @Autowired
    private DelivUtil delivUtil;


    /**
     * 购销合同类别
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult pscTypeList(@RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "15") Integer size,
                                  PscType pscType){
        try
        {
            PageInfo<PscTypeVo> resultList = pscTypeService.selectAll(page, size, pscType);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", resultList);
        } catch (ServiceException ex)
        {
            log.error("[PartnerPscTypeController][pscTypeList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSCTYPE_R, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PartnerPscTypeController][pscTypeList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSCTYPE_R, pscTypeCon+ CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加购销合同类别
     * @param pscType
     * @return
     */
    @SystemLog(name = "添加购销合同类别")
    @RequestMapping(value = "/addPscType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addPscType(@RequestBody PscType pscType, HttpServletRequest re){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS,pscTypeService.insertPscType(pscType,sysUser));
        }catch (ServiceException ex)
        {
            log.error("[PartnerPscTypeController][addPscType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSCTYPE_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PartnerPscTypeController][addPscType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSCTYPE_C, pscTypeCon+ CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改购销合同类别
     * @param pscType
     * @return
     */
    @SystemLog(name = "修改购销合同类别")
    @RequestMapping(value = "/updatePscType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatePscType(@RequestBody PscType pscType, HttpServletRequest re){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);

            return new JsonResult(JsonResultCode.SUCCESS, "操作成功",pscTypeService.updatePscType(pscType,sysUser));

        }catch (ServiceException ex)
        {
            log.error("[PartnerPscTypeController][updatePscType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSCTYPE_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PartnerPscTypeController][updatePscType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSCTYPE_U, pscTypeCon+ CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除购销合同类别
     * @param pscType
     * @return
     */
    @SystemLog(name = "删除购销合同类别")
    @RequestMapping(value = "/deletePscType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletePscType(@RequestBody PscType pscType){
        try
        {

            return new JsonResult(JsonResultCode.SUCCESS, "操作成功",pscTypeService.deletePscType(pscType));

        } catch (ServiceException ex)
        {
            log.error("[PartnerPscTypeController][deletePscType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSCTYPE_D, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PartnerPscTypeController][deletePscType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSCTYPE_D, pscTypeCon+ CommonFainl.DELETEFANS, "");
        }
    }


    /**
     * 购销申请类别中下拉框
     *
     * @return
     */
    @RequestMapping(value = "/option", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult pscTypeOption() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();
            List<OptionVo> list = prodClsUtils.getList();

            map.put("rcvWarehReqd", list);//须指定收货仓库
            map.put("pucAutoGen", list);//采购合同自动生成
            map.put("pucAutoChk", list);//采购合同自动审核
            map.put("isPuItmd", list);//是否居间采购
            map.put("isPuItmdAlt", list);//是否居间采购可选
            map.put("delivWarehReqd", list);//须指定发货仓库
            map.put("slcAutoGen", list);//销售合同自动生成
            map.put("slcAutoChk", list);//销售合同自动审核
            map.put("isSlItmd", list);//是否居间销售
            map.put("isSlItmdAlt", list);//是否居间销售可选
            map.put("tranUnitReqd", list);//须指定中转方
            map.put("drDiffJgd", sysCodeDtlService.optionGet("DR_ROLE"));//差异裁定方
            map.put("drDiffJgdAlt", list);//差异裁定方可选
            map.put("implByIns", list);//按指令执行
            map.put("implByInsAlt", list);//按指令执行可选
            map.put("multiImpl", list);//多次执行
            map.put("multiImplAlt", list);//多次执行可选
            map.put("isSpot", list);//是否现货
            map.put("isSpotAlt", list);//是否现货可选
            map.put("prcSite", sysCodeDtlService.optionGet("PRC_SITE"));//定价点
            map.put("prcSiteAlt", list);//定价点可选
            map.put("fixedUnitPrice", list);//固定单价
            map.put("rqdCtrl", sysCodeDtlService.optionGet("RQD_CTRL"));//货期控制
            map.put("rqdCtrlAlt", list);//货期控制可选
            map.put("splEnabled", list);//允许增补商品
            map.put("splEnabledAlt", list);//允许增补商品可选
            map.put("isCms", list);//是否代销
            map.put("isCmsAlt", list);//是否代销可选
            map.put("psStlMthd", sysCodeDtlService.optionGet("PS_STL_MTHD"));//结算方式
            map.put("psStlMthdAlt", list);//结算方式可选
            map.put("mfzEnabled", list);//启用冻款
            map.put("mfzEnabledAlt", list);//启用冻款可选
            map.put("psMfzSite", sysCodeDtlService.optionGet("PS_MFZ_SITE"));//冻款点
            map.put("psMfzSiteAlt", list);//冻款点可选
            map.put("gmEnabled", list);//启用保证金
            map.put("gmEnabledAlt", list);//启用保证金可选
            map.put("planCtrl", list);//计划控制
            map.put("planCtrlAlt", list);//计划控制可选
            map.put("bxiEnabled", list);//启用配码
            map.put("bxiEnabledAlt", list);//启用配码可选

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception ex) {
            log.error("[PartnerPscTypeController][pscTypeOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

}



