package com.boyu.erp.platform.usercenter.controller.warehouse;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.TfnDtl;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.warehouse.TfnDtlService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.warehouse.TfnDtlVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: boyu-platform_text
 * @description: 调拨单明细控制层
 * @author: ck
 * @create: 2019-06-28 10:26
 */
@Slf4j
@RestController
@RequestMapping("/warehouse/tfn/tfnDtl")
public class TfnDtlController extends BaseController {
    @Autowired
    private TfnDtlService tfnDtlService;

    /**
     * 查询调拨单列表
     *
     * @param tfnDtl
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult tfnDtlTypeAll(@RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "15") Integer size,
                                 @RequestBody TfnDtl tfnDtl, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            if (user  != null) {
                tfnDtl.setUnitId(user.getUnit().getUnitId());
            }
            tfnDtl.setTfnNum(request.getParameter("tfnNum"));

            PageInfo<TfnDtlVo> pageInfo = tfnDtlService.getTfnDtlList(page, size, tfnDtl);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, pageInfo);
        } catch (ServiceException ex) {
            log.error("[TfnDtlTypeService][getProductDetail] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TfnDtlTypeController][tfnDtlTypeAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 创建调拨单明细
     * @param tfnDtl
     * @param re
     * @return
     */
    @SystemLog(name = "创建调拨单明细")
    @RequestMapping(value = "/createTfnDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult createTfnDtl(@RequestBody TfnDtl tfnDtl, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            if (user != null){
                tfnDtl.setUnitId(user.getUnit().getUnitId());
            }
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", tfnDtlService.insert(tfnDtl));
        } catch (ServiceException ex) {
            log.error("[TfnService][deleteTfn] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TfnService][deleteTfn] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }

    /**
     * 更细调拨单明细
     * @param tfnDtl
     * @param re
     * @return
     */
    @SystemLog(name = "更新调拨单明细")
    @RequestMapping(value = "/updateTfnDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateTfnDtl(@RequestBody TfnDtl tfnDtl, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            if (user != null){
                tfnDtl.setUnitId(user.getUnit().getUnitId());
            }
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", tfnDtlService.update(tfnDtl));
        } catch (ServiceException ex) {
            log.error("[TfnService][deleteTfn] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TfnService][deleteTfn] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }

    /**
     * 删除调拨单明细
     * @param tfnDtl
     * @param re
     * @return
     */
    @SystemLog(name = "删除调拨单明细")
    @RequestMapping(value = "/deleteTfnDtl", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteTfnDtl(@RequestBody TfnDtl tfnDtl, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            if (user != null){
                tfnDtl.setUnitId(user.getUnit().getUnitId());
            }
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", tfnDtlService.delete(tfnDtl));
        } catch (ServiceException ex) {
            log.error("[TfnService][deleteTfn] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TfnService][deleteTfn] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }
}
