package com.boyu.erp.platform.usercenter.controller.option;

import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.warehouse.WarehOptionSerivce;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehInfoVo;
import com.boyu.erp.platform.usercenter.vo.warehouse.WarehUnitOptionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/warehouse")
public class WarehOptionController extends BaseController {
    @Autowired
    private WarehOptionSerivce warehOptionSerivce;

    /**
     * 当前用户仓库组织下拉
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/option/getOption", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getWarehOption(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                     @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                     WarehUnitOptionVo vo, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, warehOptionSerivce.getPageinfo(page, size, vo, user));
        } catch (ServiceException ex) {
            log.error("[WarehOptionSerivce][getPageinfo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehOptionController][getWarehOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH_OPENT, "仓库下拉查询异常", "");
        }
    }


    /**
     * 某个组织下仓库弹窗
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/option/getOwnerIdOption", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getOwnerIdOption(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                       @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                       WarehUnitOptionVo vo, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, warehOptionSerivce.getOwnerIdOption(page, size, vo, user));
        } catch (ServiceException ex) {
            log.error("[WarehOptionSerivce][getPageinfo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehOptionController][getWarehOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH_OPENT, "仓库下拉查询异常", "");
        }
    }


    /**
     * 公司组织下拉
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/company/getOption", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getCompanyOption(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                       @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                       WarehUnitOptionVo vo, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, warehOptionSerivce.getCompanyinfo(page, size, vo, user));
        } catch (ServiceException ex) {
            log.error("[WarehOptionSerivce][getCompanyinfo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH_COMPANY, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehOptionController][getCompanyOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH_COMPANY, "公司下拉查询异常", "");
        }
    }


    /**
     * 发货方查询(按组织筛选)
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/deliv/getOption", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getDelivOption(@RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                     @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                     WarehUnitOptionVo vo, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, warehOptionSerivce.getDelivOption(page, size, vo, user));
        } catch (ServiceException ex) {
            log.error("[WarehOptionSerivce][getCompanyinfo] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH_COMPANY, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[WarehOptionController][getDelivOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE_WAERH_COMPANY, "发货方查询异常: WarehOptionController.getDelivOption()", "");
        }
    }

}
