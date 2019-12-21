package com.boyu.erp.platform.usercenter.controller.purchase;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.purchase.PucType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysCodeDtlService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PscTypeService;
import com.boyu.erp.platform.usercenter.service.purchaseservice.PucTypeService;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.FieldUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.purchase.PucTypeVo;
import com.boyu.erp.platform.usercenter.vo.system.PurKeyValue;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: boyu-platform
 * @description: 采购合同类别控制层
 * @author: wz
 * @create: 2019-06-19 11:53
 */
@Slf4j
@RestController
@RequestMapping("/purchase/pucType")
public class PurchasePucTypeController extends BaseController {
    private static final String pucTypeCon = "采购合同类别";
    @Autowired
    private PucTypeService pucTypeService;
    @Autowired
    private PscTypeService pscTypeService;
    @Autowired
    private SysCodeDtlService sysCodeDtlService;
    @Autowired
    private FieldUtils fieldUtils;
    @Autowired
    private ProdClsUtils prodClsUtils;

    /**
     * 采购合同类别
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult pucTypeList(@RequestParam(defaultValue = "0") Integer page,
                                @RequestParam(defaultValue = "15") Integer size,
                                  String puc, String desc){
        try
        {
            PucType pucType = new PucType();
            pucType.setPucType(puc);
            pucType.setDescription(desc);
            PageInfo<PucTypeVo> resultList = pucTypeService.selectAll(page, size, pucType);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex)
        {
            log.error("[PurchasePucTypeController][pucTypeList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUCTYPE_R, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePucTypeController][pucTypeList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUCTYPE_R, pucTypeCon + CommonFainl.SELECTFIANL, "");
        }
    }



    /**
     * 添加采购合同类别
     * @param pucType
     * @return
     */
    @SystemLog(name = "添加采购合同类别")
    @RequestMapping(value = "/addPucType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addPucType(@RequestBody PucType pucType, HttpServletRequest re){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS,pucTypeService.insertPucType(pucType,sysUser));
        }catch (ServiceException ex)
        {
            log.error("[PurchasePucTypeController][addPucType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUCTYPE_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucTypeController][addPucType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUCTYPE_C, pucTypeCon+ CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改采购合同类别
     * @param pucType
     * @return
     */
    @SystemLog(name = "修改采购合同类别")
    @RequestMapping(value = "/updatePucType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updatePucType(@RequestBody PucType pucType, HttpServletRequest re){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS,pucTypeService.updatePucType(pucType,sysUser));
        }catch (ServiceException ex)
        {
            log.error("[PurchasePucTypeController][updatePucType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUCTYPE_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[PurchasePucTypeController][updatePucType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUCTYPE_U, pucTypeCon+ CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除采购合同类别
     * @param pucType
     * @return
     */
    @SystemLog(name = "删除采购合同类别")
    @RequestMapping(value = "/deletePucType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deletePucType(@RequestBody PucType pucType, HttpServletRequest re){
        try
        {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS,pucTypeService.deletePucType(pucType,sysUser));
        } catch (ServiceException ex)
        {
            log.error("[PurchasePucTypeController][deletePucType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUCTYPE_D, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[PurchasePucTypeController][deletePucType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_PUCTYPE_D, pucTypeCon+ CommonFainl.DELETEFANS, "");
        }
    }

    /**
     * 采购合同类别中购销合同类别下拉框
     *
     * @return
     */
    @RequestMapping(value = "/option", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult psiTypeOption() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();

            map.put("pscList", pscTypeService.optionGet());
            map.put("pbmList", sysCodeDtlService.optionGet("BRDG_MODE"));
            map.put("pbmaList", prodClsUtils.getList());
            map.put("arList", prodClsUtils.getList());
            map.put("araList", prodClsUtils.getList());
            map.put("acList", prodClsUtils.getList());

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception ex) {
            log.error("[PurchaseVdrAttrDefController][psiTypeOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 查询采购合同类别必填字段
     *
     * @return
     */
    @RequestMapping(value = "/getFile", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getFile() {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("PUC_TYPE"));
        } catch (Exception ex) {
            log.error("[ProductCatController][getProdClsFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }


}



