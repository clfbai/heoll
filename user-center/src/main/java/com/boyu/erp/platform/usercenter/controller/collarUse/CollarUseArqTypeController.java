package com.boyu.erp.platform.usercenter.controller.collarUse;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.collarUse.ArqType;
import com.boyu.erp.platform.usercenter.entity.purchase.PucType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.collarUseservice.ArqTypeService;
import com.boyu.erp.platform.usercenter.utils.goods.ProdClsUtils;
import com.boyu.erp.platform.usercenter.utils.purchase.FieldUtils;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.purchase.PucTypeVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * @program: CollarUseArqTypeController
 * @description: 领用单控制层
 * @author: wz
 * @create: 2019-8-23 12:11
 */
@Slf4j
@RestController
@RequestMapping("/collarUse/arqType")
public class CollarUseArqTypeController extends BaseController {
    private static final String arqTypeCon = "领用单类别";

    @Autowired
    private ArqTypeService arqTypeService;

    @Autowired
    private ProdClsUtils prodClsUtils;

    @Autowired
    private FieldUtils fieldUtils;

    /**
     * 领用单类别
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult arqTypeList(@RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "15") Integer size,
                                  ArqType arqType) {
        try {

            PageInfo<ArqType> resultList = arqTypeService.selectAll(page, size, arqType);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex) {
            log.error("[CollarUseArqTypeController][arqTypeList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_ARQTYPE_R, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[CollarUseArqTypeController][arqTypeList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_ARQTYPE_R, arqTypeCon + CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加领用单类别
     *
     * @param arqType
     * @return
     */
    @SystemLog(name = "添加领用单类别")
    @RequestMapping(value = "/addArqType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addArqType(@RequestBody ArqType arqType, HttpServletRequest re) {
        try {

            SysUser sysUser = (SysUser) this.isNullUser(re);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, arqTypeService.insertArqType(arqType,sysUser));
        } catch (ServiceException ex) {
            log.error("[CollarUseArqTypeController][addArqType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_ARQTYPE_C, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[CollarUseArqTypeController][addArqType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_ARQTYPE_C, arqTypeCon + CommonFainl.ADDFIANL, "");
        }
    }


    /**
     * 修改领用单类别
     *
     * @param arqType
     * @return
     */
    @SystemLog(name = "修改领用单类别")
    @RequestMapping(value = "/updateArqType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateArqType(@RequestBody ArqType arqType, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, arqTypeService.updateArqType(arqType,sysUser));
        } catch (ServiceException ex) {
            log.error("[CollarUseArqTypeController][updateArqType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_ARQTYPE_U, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[CollarUseArqTypeController][updateArqType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_ARQTYPE_U, arqTypeCon + CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除领用单类别
     *
     * @param arqType
     * @return
     */
    @SystemLog(name = "删除领用单类别")
    @RequestMapping(value = "/deleteArqType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteArqType(@RequestBody ArqType arqType, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, arqTypeService.deleteArqType(arqType,sysUser));
        } catch (ServiceException ex) {
            log.error("[CollarUseArqTypeController][deleteArqType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_ARQTYPE_D, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[CollarUseArqTypeController][deleteArqType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_ARQTYPE_D, arqTypeCon + CommonFainl.DELETEFANS, "");
        }
    }


    /**
     * 领用单类别中下拉框
     *
     * @return
     */
    @RequestMapping(value = "/option", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult psiTypeOption() {
        try {
            Map<String, Object> map = prodClsUtils.getMap();


            map.put("delivWarehReqd", prodClsUtils.getList());//指定发货仓库
            map.put("bxiEnabled", prodClsUtils.getList());//启用配码
            map.put("active", prodClsUtils.getList());//是否活动
            map.put("bxiEnabledAlt", prodClsUtils.getList());//启用配码可选

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, map);
        } catch (Exception ex) {
            log.error("[CollarUseArqTypeController][option] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }


    /**
     * 查询领用单类别必填字段
     *
     * @return
     */
    @RequestMapping(value = "/getFile", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getFile() {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, fieldUtils.flie("ARQ_TYPE"));
        } catch (Exception ex) {
            log.error("[ProductCatController][getProdClsFile] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

}




