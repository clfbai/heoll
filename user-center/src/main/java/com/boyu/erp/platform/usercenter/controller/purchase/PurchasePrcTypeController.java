package com.boyu.erp.platform.usercenter.controller.purchase;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.purchase.PrcType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PrcTypeService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.RtcTypeService;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.FieldUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.purchase.PrcTypeVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



import javax.servlet.http.HttpServletRequest;

import java.util.Map;


/**
 * @program: boyu-platform
 * @description: 退购合同类别控制层
 * @author: wz
 * @create: 2019-06-20 09:57
 */
@Slf4j
@RestController
@RequestMapping("/purchase/prcType")
public class



PurchasePrcTypeController extends BaseController {
    private static final String prcTypeCon = "退购合同类别";
    @Autowired
    private PrcTypeService prcTypeService;
    @Autowired
    private RtcTypeService rtcTypeService;
    @Autowired
    private SysCodeDtlService sysCodeDtlService;
    @Autowired
    private FieldUtils fieldUtils;
    @Autowired
    private ProdClsUtils prodClsUtils;

    /**
     * 退购合同类别
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult prcTypeList(@RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "15") Integer size,
                                  String prc, String desc){
        try
        {
            PrcType prcType = new PrcType();
            prcType.setPrcType(prc);
            prcType.setDescription(desc);
            PageInfo<PrcTypeVo> resultList = prcTypeService.selectAll(page, size, prcType);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex)
        {
            log.error("[PurchasePrcTypeController][prcTypeList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PRCTYPE_C, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePrcTypeController][prcTypeList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PRCTYPE_C, prcTypeCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加退购合同类别
     * @param prcType
     * @return
     */
    @SystemLog(name = "添加退购合同类别")
    @RequestMapping(value = "/addPrcType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addPrcType(@RequestBody PrcType prcType, HttpServletRequest re){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS,prcTypeService.insertPrcType(prcType,sysUser));
        }catch (ServiceException ex)
        {
            log.error("[PurchasePrcTypeController][addPrcType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PRCTYPE_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcTypeController][addPrcType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PRCTYPE_R, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 修改退购合同类别
     * @param prcType
     * @return
     */
    @SystemLog(name = "修改退购合同类别")
    @RequestMapping(value = "/updatePrcType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatePrcType(@RequestBody PrcType prcType, HttpServletRequest re){
        try
        {
            SysUser user = (SysUser) this.isNullUser(re);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS,prcTypeService.updatePrcType(prcType,user));
        }catch (ServiceException ex)
        {
            log.error("[PurchasePrcTypeController][updatePrcType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PRCTYPE_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePrcTypeController][updatePrcType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PRCTYPE_U, prcTypeCon+ CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除退购合同类别
     * @param prcType
     * @return
     */
    @SystemLog(name = "删除退购合同类别")
    @RequestMapping(value = "/deletePrcType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletePrcType(@RequestBody PrcType prcType, HttpServletRequest re){
        try
        {
            SysUser user = (SysUser) this.isNullUser(re);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS,prcTypeService.deletePrcType(prcType,user));
        } catch (ServiceException ex)
        {
            log.error("[PurchasePrcTypeController][deletePrcType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PRCTYPE_D, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePrcTypeController][deletePrcType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PRCTYPE_D, prcTypeCon+ CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 退购合同类别中退货合同类别下拉框
     *
     * @return
     */
    @RequestMapping(value = "/option", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult option() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();

            map.put("rtcList",  rtcTypeService.optionGet());
            map.put("pbmList", sysCodeDtlService.optionGet("BRDG_MODE"));
            map.put("pbmaList", prodClsUtils.getList());
            map.put("arList", prodClsUtils.getList());
            map.put("araList", prodClsUtils.getList());
            map.put("acList", prodClsUtils.getList());

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception ex) {
            log.error("[PurchasePrcTypeController][rtcTypeOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 查询退购合同类别必填字段
     *
     * @return
     */
    @RequestMapping(value = "/getFile", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getFile() {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("PRC_TYPE"));
        } catch (Exception ex) {
            log.error("[ProductCatController][getProdClsFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }


}



