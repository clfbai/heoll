package com.boyu.erp.platform.usercenter.controller.partner;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.purchase.RtcType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.RtcTypeService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.utils.warehouse.DelivUtil;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import com.boyu.erp.platform.usercenter.vo.partner.RtcTypeVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * @program: boyu-platform
 * @description: 退货合同控制层
 * @author: wz
 * @create: 2019-8-21 10:15
 */
@Slf4j
@RestController
@RequestMapping("/partner/rtcType")
public class PartnerRtcTypeController extends BaseController {
    private static final String rtcTypeCon = "退货合同类别";

    @Autowired
    private ProdClsUtils prodClsUtils;
    @Autowired
    private RtcTypeService rtcTypeService;
    @Autowired
    private DelivUtil delivUtil;
    @Autowired
    private SysCodeDtlService sysCodeDtlService;


    /**
     * 退货合同类别
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult rtcTypeList(@RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "15") Integer size,
                                  RtcType rtcType){
        try
        {
            PageInfo<RtcTypeVo> resultList = rtcTypeService.selectAll(page, size, rtcType);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", resultList);
        } catch (ServiceException ex)
        {
            log.error("[PartnerRtcTypeController][rtcTypeList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTCTYPE_R, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PartnerRtcTypeController][rtcTypeList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTCTYPE_R, rtcTypeCon+ CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加退货合同类别
     * @param rtcType
     * @return
     */
    @SystemLog(name = "添加退货合同类别")
    @RequestMapping(value = "/addRtcType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addRtcType(@RequestBody RtcType rtcType, HttpServletRequest re){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS,rtcTypeService.insertRtcType(rtcType,sysUser));
        }catch (ServiceException ex)
        {
            log.error("[PartnerRtcTypeController][addRtcType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTCTYPE_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PartnerRtcTypeController][addRtcType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTCTYPE_C, rtcTypeCon+ CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改退货合同类别
     * @param rtcType
     * @return
     */
    @SystemLog(name = "修改退货合同类别")
    @RequestMapping(value = "/updateRtcType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateRtcType(@RequestBody RtcType rtcType, HttpServletRequest re){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);

            return new JsonResult(JsonResultCode.SUCCESS, "操作成功",rtcTypeService.updateRtcType(rtcType,sysUser));

        }catch (ServiceException ex)
        {
            log.error("[PartnerRtcTypeController][updateRtcType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTCTYPE_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PartnerRtcTypeController][updateRtcType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTCTYPE_U, rtcTypeCon+ CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除退货合同类别
     * @param rtcType
     * @return
     */
    @SystemLog(name = "删除退货合同类别")
    @RequestMapping(value = "/deleteRtcType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteRtcType(@RequestBody RtcType rtcType){
        try
        {

            return new JsonResult(JsonResultCode.SUCCESS, "操作成功",rtcTypeService.deleteRtcType(rtcType));

        } catch (ServiceException ex)
        {
            log.error("[PartnerRtcTypeController][deleteRtcType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTCTYPE_D, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PartnerRtcTypeController][deleteRtcType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_RTCTYPE_D, rtcTypeCon+ CommonFainl.DELETEFANS, "");
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

            map.put("pscReqd", list);//关联购销合同
            map.put("rcvWarehReqd", list);//须指定收货仓库
            map.put("prcAutoGen", list);//退购合同自动生成
            map.put("prcAutoChk", list);//退购合同自动审核
            map.put("isPrItmd", list);//是否居间退购
            map.put("isPrItmdAlt", list);//是否居间退购可选
            map.put("delivWarehReqd", list);//须指定发货仓库
            map.put("srcAutoGen", list);//退销合同自动生成
            map.put("srcAutoChk", list);//退销合同自动审核
            map.put("isSrItmd", list);//是否居间退销
            map.put("isSrItmdAlt", list);//是否居间退销可选
            map.put("tranUnitReqd", list);//须指定中转方
            map.put("drDiffJgd", sysCodeDtlService.optionGet("DR_ROLE"));//差异裁定方
            map.put("drDiffJgdAlt", list);//差异裁定方可选
            map.put("multiImpl", list);//多次执行
            map.put("multiImplAlt", list);//多次执行可选
            map.put("prcSite", sysCodeDtlService.optionGet("PRC_SITE"));//定价点
            map.put("prcSiteAlt", list);//定价点可选
            map.put("fixedUnitPrice", list);//固定单价
            map.put("splEnabled", list);//允许增补商品
            map.put("splEnabledAlt", list);//允许增补商品可选
            map.put("isCms", list);//是否代销
            map.put("isCmsAlt", list);//是否代销可选
            map.put("rtnaInvd", list);//占用可退额度
            map.put("rtnaInvdAlt", list);//占用可退额度可选
            map.put("accEnabled", list);//启用授信
            map.put("accEnabledAlt", list);//启用授信可选
            map.put("rtnAccSite", sysCodeDtlService.optionGet("RTN_ACC_SITE"));//授信点
            map.put("rtnAccSiteAlt", list);//授信点可选
            map.put("bxiEnabled", list);//启用配码
            map.put("bxiEnabledAlt", list);//启用配码可选

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception ex) {
            log.error("[PartnerPscTypeController][pscTypeOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

}



