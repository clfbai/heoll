package com.boyu.erp.platform.usercenter.controller.warehouse;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.warehouse.TfnType;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.warehouse.TfnTypeService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.boyu.erp.platform.usercenter.vo.OptionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: boyu-platform_text
 * @description: 调拨单类别控制层
 * @author: ck
 * @create: 2019-06-25 10:26
 */
@Slf4j
@RestController
@RequestMapping("/warehouse/tfnType")
public class TfnTypeController extends BaseController {
    @Autowired
    private TfnTypeService tfnTypeService;

    /**
     * 查询调拨单类别列表
     *
     * @param tfnType
     * @return
     */
    @RequestMapping(value = "/all", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult tfnTypeAll(TfnType tfnType,
                                 @RequestParam(defaultValue = CommonFainl.PAGE) Integer page,
                                 @RequestParam(defaultValue = CommonFainl.SIZE) Integer size,
                                 HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, tfnTypeService.getAll(tfnType, page, size));
        } catch (ServiceException ex) {
            log.error("[TfnTypeService][getProductDetail] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TfnTypeController][tfnTypeAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 增加调拨单类别
     *
     * @param tfnType
     * @return
     */
    @SystemLog(name = "增加调拨单类别")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addTypeAll(@RequestBody TfnType tfnType, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.ADDSTUS, tfnTypeService.insertTfnType(tfnType, this.getSessionSysUser(request)));
        } catch (ServiceException ex) {
            log.error("[TfnTypeService][insertTfnType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TfnTypeController][addTypeAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.ADDFIANL, "");
        }
    }

    /**
     * 修改调拨单类别
     *
     * @param tfnType
     * @return
     */
    @SystemLog(name = "修改调拨单类别")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateTypeAll(@RequestBody TfnType tfnType, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.UPDATESTUS, tfnTypeService.updateTfnType(tfnType, this.getSessionSysUser(request)));
        } catch (ServiceException ex) {
            log.error("[TfnTypeService][updateTfnType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TfnTypeController][updateTypeAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.UPDATEFANS, "");
        }
    }

    /**
     * 删除调拨单类别
     *
     * @param tfnType
     * @return
     */
    @SystemLog(name = "删除调拨单类别")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteTypeAll(@RequestBody TfnType tfnType, HttpServletRequest request) {
        try {
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.DELETESTUAS, tfnTypeService.deleteTfnType(tfnType, this.getSessionSysUser(request)));
        } catch (ServiceException ex) {
            log.error("[TfnTypeService][deleteTfnType] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[TfnTypeController][deleteTypeAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.DELETEFANS, "");
        }
    }


    @RequestMapping(value = "/getTfnTypeOption", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult getTfnTypeOption() {
        try {
            Map<String, List<OptionVo>> map = new HashMap<>();

            /**
             * 获取须指定会计日期option
             */
            List<OptionVo> fsclDateReqd = new ArrayList<>();
            fsclDateReqd.add(new OptionVo("是", "T"));
            fsclDateReqd.add(new OptionVo("否", "F"));

            /**
             * 获取差异裁定方option
             */
            List<OptionVo> drDiffJgd = new ArrayList<>();
            drDiffJgd.add(new OptionVo("发货方", "D"));
            drDiffJgd.add(new OptionVo("收货方", "F"));

            /**
             * 获取差异裁定方可选option
             */
            List<OptionVo> drDiffJgdAlt = new ArrayList<>();
            drDiffJgdAlt.add(new OptionVo("是", "T"));
            drDiffJgdAlt.add(new OptionVo("否", "F"));

            /**
             * 获取启用配码option
             */
            List<OptionVo> bxiEnabled = new ArrayList<>();
            bxiEnabled.add(new OptionVo("是", "T"));
            bxiEnabled.add(new OptionVo("否", "F"));

            /**
             * 获取启用配码可选option
             */
            List<OptionVo> bxiEnabledAlt = new ArrayList<>();
            bxiEnabledAlt.add(new OptionVo("是", "T"));
            bxiEnabledAlt.add(new OptionVo("否", "F"));

            /**
             * 获取活动option
             */
            List<OptionVo> active = new ArrayList<>();
            active.add(new OptionVo("是", "T"));
            active.add(new OptionVo("否", "F"));


            map.put("fsclDateReqd", fsclDateReqd);
            map.put("drDiffJgd", drDiffJgd);
            map.put("drDiffJgdAlt", drDiffJgdAlt);
            map.put("bxiEnabled", bxiEnabled);
            map.put("bxiEnabledAlt", bxiEnabledAlt);
            map.put("active", active);

            return new JsonResult(JsonResultCode.SUCCESS, "获取信息成功", map);
        } catch (Exception ex) {
            log.error("[UserController][getUserOption] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "获取信息失败", "");
        }
    }
}
