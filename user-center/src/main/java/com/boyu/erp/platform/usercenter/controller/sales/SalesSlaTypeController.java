package com.boyu.erp.platform.usercenter.controller.sales;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.sales.SlaType;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.salesservice.SlaTypeService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.sales.SlaTypeVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @program: boyu-platform
 * @description: 销售申请控制层
 * @author: wz
 * @create: 2019-06-18 14:15
 */
@Slf4j
@RestController
@RequestMapping("/sales/slaType")
public class SalesSlaTypeController extends BaseController {
    private static final String puoCon = "销售申请";
    @Autowired
    private SlaTypeService slaTypeService;


    /**
     * 销售申请类别
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public JsonResult slaTypeList(@RequestParam(defaultValue = "0") Integer page,
                                  @RequestParam(defaultValue = "15") Integer size,
                                  SlaTypeVo slaType) {
        try {
            PageInfo<SlaTypeVo> resultList = slaTypeService.selectAll(page, size, slaType);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, resultList);
        } catch (ServiceException ex) {
            log.error("[SalesSlaTypeController][slaTypeList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlaTypeController][slaTypeList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, puoCon+CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 添加销售申请类别
     *
     * @param slaType
     * @return
     */
    @SystemLog(name = "添加销售申请类别")
    @RequestMapping(value = "/addSlaType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addSlaType(@RequestBody SlaType slaType, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, slaTypeService.insertSlaType(slaType, sysUser));

        } catch (ServiceException ex) {
            log.error("[SalesSlaTypeController][addSlaType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlaTypeController][addSlaType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, puoCon+CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改销售申请类别
     *
     * @param slaType
     * @return
     */
    @SystemLog(name = "修改销售申请类别")
    @RequestMapping(value = "/updateSlaType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateSlaType(@RequestBody SlaType slaType, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);

            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, slaTypeService.updateSlaType(slaType, sysUser));

        } catch (ServiceException ex) {
            log.error("[SalesSlaTypeController][updateSlaType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlaTypeController][updateSlaType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, puoCon+CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除销售申请类别
     *
     * @param slaType
     * @return
     */
    @SystemLog(name = "删除销售申请类别")
    @RequestMapping(value = "/deleteSlaType", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteSlaType(@RequestBody SlaType slaType, HttpServletRequest re) {
        try {
            SysUser sysUser = (SysUser) this.isNullUser(re);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, slaTypeService.deleteSlaType(slaType, sysUser));

        } catch (ServiceException ex) {
            log.error("[SalesSlaTypeController][deleteSlaType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SalesSlaTypeController][deleteSlaType] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, puoCon+CommonFainl.DELETEFANS, "");
        }
    }

}



