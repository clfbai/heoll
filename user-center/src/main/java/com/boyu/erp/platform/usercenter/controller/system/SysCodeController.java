package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysCode;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysCodeService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @description: 代码控制层
 * @author: js
 */
@Slf4j
@RestController
@RequestMapping("/sys")
public class SysCodeController extends BaseController {

    @Autowired
    private SysCodeService sysCodeService;

    /**
     * 查询代码
     *
     * @param page
     * @param size
     * @param sysCode
     * @return
     */
    @RequestMapping(value = "/code/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult codeList(@RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "15") Integer size,
                               SysCode sysCode) {
        try {

            PageInfo<SysCode> pageInfo = sysCodeService.selectAll(page, size, sysCode);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", pageInfo);
        } catch (Exception ex) {
            log.error("[SysCodeController][codeList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 修改代码
     *
     * @param sysCode
     * @return
     */
    @SystemLog(name = "修改代码")
    @RequestMapping(value = "/update/code", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateCode(HttpServletRequest request, @RequestBody SysCode sysCode) {
        try {
            SysUser sysUser = this.getSessionSysUser(request);
            sysCode.setUpdateBy(sysUser.getUserId());
            sysCode.setUpdateTime(new Date());
            sysCode.setIsDel(CommonFainl.BTYPESTATUS);
            sysCodeService.updateSysCode(sysCode);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
        } catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        }catch (Exception ex) {
            log.error("[SysCodeController][updateCode] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 删除代码
     *
     * @param sysCode
     * @return
     */
    @SystemLog(name = "删除代码")
    @RequestMapping(value = "/delete/code", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteCode(HttpServletRequest request, @RequestBody SysCode sysCode) {
        try {
            SysUser sysUser = this.getSessionSysUser(request);
            sysCode.setUpdateBy(sysUser.getUserId());
            sysCode.setUpdateTime(new Date());
            sysCode.setIsDel(CommonFainl.FAILSTATUS);
            sysCodeService.updateSysCode(sysCode);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
        }catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SysCodeController][deleteCode] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 增加代码
     *
     * @param sysCode
     * @return
     */
    @SystemLog(name = "增加代码")
    @RequestMapping(value = "/add/code", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addCode(HttpServletRequest request, @RequestBody SysCode sysCode) {
        try {
            SysUser sysUser = this.getSessionSysUser(request);
            if (!isExitCode(sysCode)) {
                return new JsonResult(JsonResultCode.FAILURE, "代码已存在请勿重复添加", "");
            }
            sysCode.setCreateTime(new Date());
            sysCode.setIsDel(CommonFainl.BTYPESTATUS);
            sysCode.setUpdateBy(sysUser.getUserId());
            sysCode.setCreateBy(sysUser.getUserId());
            sysCode.setUpdateTime(new Date());
            sysCodeService.insertSysCode(sysCode);
            return new JsonResult(JsonResultCode.SUCCESS, "操作成功", "");
        }catch (ServiceException ex) {
            log.error("[PrivDelSerivce][deletePrivDel] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[SysCodeController][addCode] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "操作失败", "");
        }
    }

    /**
     * 是否已存在代码信息
     *
     * @param sysCode
     * @return
     */
    private boolean isExitCode(SysCode sysCode) {
        SysCode code = sysCodeService.getSysCode(sysCode);
        return code == null ? true : false;
    }
}
