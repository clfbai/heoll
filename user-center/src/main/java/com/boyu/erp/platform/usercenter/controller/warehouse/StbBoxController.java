package com.boyu.erp.platform.usercenter.controller.warehouse;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.entity.warehouse.StbBox;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.warehouse.StbBoxService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: boyu-platform_text
 * @description: 入库单装箱控制层
 * @author: ck
 * @create: 2019-06-28 10:26
 */
@Slf4j
@RestController
@RequestMapping("/warehouse/stb/stbBox")
public class StbBoxController extends BaseController {
    @Autowired
    private StbBoxService stbBoxService;

    /**
     * 查询库存单装箱列表
     *
     * @param stbBox
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult stbBoxAll(@RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "15") Integer size,
                                 @RequestBody StbBox stbBox, HttpServletRequest request) {
        try {
            SysUser user = (SysUser) this.isNullUser(request);
            if (user  != null) {
                stbBox.setUnitId(user.getUnit().getUnitId());
            }
            PageInfo<StbBox> pageInfo = stbBoxService.getStbBoxList(page, size, stbBox);
            return new JsonResult(JsonResultCode.SUCCESS, CommonFainl.SELECTSTUS, pageInfo);
        } catch (ServiceException ex) {
            log.error("[StbBoxTypeService][getProductDetail] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[StbBoxTypeController][stbBoxTypeAll] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, CommonFainl.SELECTFIANL, "");
        }
    }

    /**
     * 创建库存单装箱
     * @param stbBox
     * @param re
     * @return
     */
    @SystemLog(name = "创建库存单装箱")
    @RequestMapping(value = "/createStbBox", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult createStbBox(@RequestBody StbBox stbBox, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            if (user != null){
                stbBox.setUnitId(user.getUnit().getUnitId());
            }
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", stbBoxService.insert(stbBox));
        } catch (ServiceException ex) {
            log.error("[StbBoxService][createStdBox] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[StbBoxService][createStdBox] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }

    /**
     * 更新库存单装箱
     * @param stbBox
     * @param re
     * @return
     */
    @SystemLog(name = "更新库存单装箱")
    @RequestMapping(value = "/updateStbBox", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateStbBox(@RequestBody StbBox stbBox, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            if (user != null){
                stbBox.setUnitId(user.getUnit().getUnitId());
            }
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", stbBoxService.update(stbBox));
        } catch (ServiceException ex) {
            log.error("[StbBoxService][updateStbBox] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[StbBoxService][updateStbBox] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }

    /**
     * 删除库存单装箱
     * @param stbBox
     * @param re
     * @return
     */
    @SystemLog(name = "删除库存单装箱")
    @RequestMapping(value = "/deleteStbBox", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteStbBox(@RequestBody StbBox stbBox, HttpServletRequest re) {
        try {
            SysUser user = (SysUser) this.isNullUser(re);
            if (user != null){
                stbBox.setUnitId(user.getUnit().getUnitId());
            }
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", stbBoxService.delete(stbBox));
        } catch (ServiceException ex) {
            log.error("[StbBoxService][deleteStbBox] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[StbBoxService][deleteStbBox] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作异常", "");
        }
    }
}
