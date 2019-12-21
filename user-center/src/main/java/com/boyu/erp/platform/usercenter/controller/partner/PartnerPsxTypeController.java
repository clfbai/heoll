package com.boyu.erp.platform.usercenter.controller.partner;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.purchase.PsxType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PscTypeService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PsxTypeService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.utils.warehouse.DelivUtil;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.partner.PsxTypeVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * @program: boyu-platform
 * @description: 购销申请控制层
 * @author: wz
 * @create: 2019-8-21 10:15
 */
@Slf4j
@RestController
@RequestMapping("/partner/psxType")
public class PartnerPsxTypeController extends BaseController {
    private static final String psxTypeCon = "购销申请类别";

    @Autowired
    private PsxTypeService psxTypeService;
    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private PscTypeService pscTypeService;
    @Autowired
    private SysCodeDtlService sysCodeDtlService;
    @Autowired
    private DelivUtil delivUtil;


    /**
     * 购销申请类别
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult psxTypeList(@RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "15") Integer size,
                                  PsxType psxType){
        try
        {
            PageInfo<PsxTypeVo> resultList = psxTypeService.selectAll(page, size, psxType);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", resultList);
        } catch (ServiceException ex)
        {
            log.error("[PartnerPsxTypeController][psxTypeList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSXTYPE_R, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PartnerPsxTypeController][psxTypeList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSXTYPE_R, psxTypeCon+ CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加购销申请类别
     * @param psxType
     * @return
     */
    @SystemLog(name = "添加购销申请类别")
    @RequestMapping(value = "/addPsxType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addPsxType(@RequestBody PsxType psxType, HttpServletRequest re){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS,psxTypeService.insertPsxType(psxType,sysUser));
        }catch (ServiceException ex)
        {
            log.error("[PartnerPsxTypeController][addPsxType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSXTYPE_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PartnerPsxTypeController][addPsxType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSXTYPE_C, psxTypeCon+ CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改购销申请类别
     * @param psxType
     * @return
     */
    @SystemLog(name = "修改购销申请类别")
    @RequestMapping(value = "/updatePsxType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatePsxType(@RequestBody PsxType psxType, HttpServletRequest re){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);

            return new JsonResult(JsonResultCode.SUCCESS, "操作成功",psxTypeService.updatePsxType(psxType,sysUser));

        }catch (ServiceException ex)
        {
            log.error("[PartnerPsxTypeController][updatePsxType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSXTYPE_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PartnerPsxTypeController][updatePsxType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSXTYPE_U, psxTypeCon+ CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除购销申请类别
     * @param psxType
     * @return
     */
    @SystemLog(name = "删除购销申请类别")
    @RequestMapping(value = "/deletePsxType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletePsxType(@RequestBody PsxType psxType){
        try
        {

            return new JsonResult(JsonResultCode.SUCCESS, "操作成功",psxTypeService.deletePsxType(psxType));

        } catch (ServiceException ex)
        {
            log.error("[PartnerPsxTypeController][deletePsxType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSXTYPE_D, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PartnerPsxTypeController][deletePsxType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PSXTYPE_D, psxTypeCon+ CommonFainl.DELETEFANS, "");
        }
    }


    /**
     * 购销申请类别中下拉框
     *
     * @return
     */
    @RequestMapping(value = "/option", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult psxTypeOption() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();
            List<OptionVo> list = prodClsUtils.getList();

            map.put("pscType", pscTypeService.optionGet());//购销合同类别下拉

            map.put("vdeReqd", list);//指定采购商
            map.put("rcvWarehReqd", list);//指定收货仓库
            map.put("puaAutoGen", list);//采购申请自动生成
            map.put("puaAutoChk", list);//采购申请自动审核
            map.put("vdrReqd", list);//指定供应商
            map.put("delivWarehReqd", list);//指定发货仓库
            map.put("slaAutoGen", list);//销售申请自动生成
            map.put("slaAutoChk", list);//销售申请自动审核
            map.put("rqdCtrl", sysCodeDtlService.optionGet("RQD_CTRL"));//货期控制
            map.put("rqdCtrlAlt", list);//货期控制可选
            map.put("bxiEnabled", list);//启用配码
            map.put("bxiEnabledAlt", list);//启动配码可选

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception ex) {
            log.error("[PartnerPsxTypeController][psxTypeOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

}



