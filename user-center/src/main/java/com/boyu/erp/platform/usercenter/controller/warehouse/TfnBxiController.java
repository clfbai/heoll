package com.boyu.erp.platform.usercenter.controller.warehouse;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.TfnBxi;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.warehouse.TfnBxiService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.warehouse.TfnBxiVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: boyu-platform_text
 * @description: 调拨单配码控制层
 * @author: ck
 * @create: 2019-06-28 10:26
 */
@Slf4j
@RestController
@RequestMapping("/warehouse/tfn/tfnBxi")
public class TfnBxiController extends BaseController {
    @Autowired
    private TfnBxiService tfnBxiService;

    /**
     * 查询调拨单配码明细列表
     *
     * @param tfnBxi
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult tfnBxiTypeAll(@RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "15") Integer size,
                                 @RequestBody TfnBxi tfnBxi, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            if (user  != null) {
                tfnBxi.setUnitId(user.getUnit().getUnitId());
            }
            PageInfo<TfnBxiVo> pageInfo = tfnBxiService.getTfnBxiList(page, size, tfnBxi);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, pageInfo);
        } catch (ServiceException ex) {
            log.error("[TfnBxiService][tfnBxiAll] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TfnBxiController][tfnBxiAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 创建调拨单配码
     * @param tfnBxi
     * @param re
     * @return
     */
    @SystemLog(name = "创建调拨单配码")
    @RequestMapping(value = "/createTfnBxi", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult createTfnBxi(@RequestBody TfnBxi tfnBxi, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            if (user != null){
                tfnBxi.setUnitId(user.getUnit().getUnitId());
            }
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", tfnBxiService.insert(tfnBxi));
        } catch (ServiceException ex) {
            log.error("[TfnService][deleteTfnBxi] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TfnService][deleteTfnBxi] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }

    /**
     * 更新调拨单配码
     * @param tfnBxi
     * @param re
     * @return
     */
    @SystemLog(name = "更新调拨单配码")
    @RequestMapping(value = "/updateTfnBxi", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateTfnBxi(@RequestBody TfnBxi tfnBxi, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            if (user != null){
                tfnBxi.setUnitId(user.getUnit().getUnitId());
            }
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", tfnBxiService.update(tfnBxi));
        } catch (ServiceException ex) {
            log.error("[TfnService][deleteTfn] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TfnService][deleteTfn] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }

    /**
     * 删除调拨单配码
     * @param tfnBxi
     * @param re
     * @return
     */
    @SystemLog(name = "删除调拨单配码")
    @RequestMapping(value = "/deleteTfnBxi", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteTfnBxi(@RequestBody TfnBxi tfnBxi, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            if (user != null){
                tfnBxi.setUnitId(user.getUnit().getUnitId());
            }
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", tfnBxiService.delete(tfnBxi));
        } catch (ServiceException ex) {
            log.error("[TfnService][deleteTfn] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TfnService][deleteTfn] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }
}
