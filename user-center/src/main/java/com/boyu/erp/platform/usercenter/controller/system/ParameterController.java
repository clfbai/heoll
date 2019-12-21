package com.boyu.erp.platform.usercenter.controller.system;

import com.boyu.erp.platform.usercenter.components.SystemLog;
import com.boyu.erp.platform.usercenter.controller.base.BaseController;
import com.boyu.erp.platform.usercenter.entity.system.SysParameter;
import com.boyu.erp.platform.usercenter.entity.system.SysUser;
import com.boyu.erp.platform.usercenter.exception.ServiceException;
import com.boyu.erp.platform.usercenter.result.JsonResult;
import com.boyu.erp.platform.usercenter.result.JsonResultCode;
import com.boyu.erp.platform.usercenter.service.system.SysParameterService;
import com.boyu.erp.platform.usercenter.vo.CommonFainl;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @program:
 * @description: 参数控制层
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class ParameterController extends BaseController {

    @Autowired
    private SysParameterService parameterService;

    /**
     * 参数分页
     *
     * @param request
     * @param page      当前页
     * @param size      每页显示多少条
     * @param parameter
     * @return
     */
    @RequestMapping(value = "/parameter/list", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult parameterList(HttpServletRequest request,
                                    @RequestParam(defaultValue = "0") Integer page,
                                    @RequestParam(defaultValue = "15") Integer size,
                                    SysParameter parameter) {
        try {
            SysUser user = this.getSessionSysUser(request);
            PageInfo<SysParameter> resultList = this.parameterService.pageList(page, size, parameter);
            return new JsonResult(JsonResultCode.SUCCESS, "查询成功", resultList);
        } catch (ServiceException ex) {
            log.error("[ParameterController][parameterList] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ParameterController][parameterList] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 增加参数
     *
     * @param request
     * @param parameter
     * @return
     */
    @SystemLog(name = "增加参数")
    @RequestMapping(value = "/parameter/add", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult addParameter(HttpServletRequest request,
                                   @RequestBody SysParameter parameter) {
        try {
           if(!findByParam(parameter)){
               return new JsonResult(JsonResultCode.FAILURE, "参数已存在", "");
           }
            SysUser user = this.getSessionSysUser(request);
            parameter.setCreateBy(user.getUserId());
            parameter.setCreateTime(new Date());
            parameter.setUpdateBy(user.getUserId());
            parameter.setUpdateTime(new Date());
            parameter.setIsDel(CommonFainl.BTYPESTATUS);
            int a = this.parameterService.insertParameter(parameter);
            return new JsonResult(JsonResultCode.SUCCESS, "增加成功", a);
        } catch (ServiceException ex) {
            log.error("[ParameterController][addParameter] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ParameterController][addParameter] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 修改参数
     *
     * @param request
     * @param parameter
     * @return
     */
    @SystemLog(name = "修改参数")
    @RequestMapping(value = "/parameter/update", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult updateParameter(HttpServletRequest request,
                                      @RequestBody SysParameter parameter) {
        try {
            SysUser user = this.getSessionSysUser(request);
            parameter.setUpdateBy(user.getUserId());
            parameter.setUpdateTime(new Date());
            int a = this.parameterService.updateParameter(parameter);
            return new JsonResult(JsonResultCode.SUCCESS, "修改成功", a);
        } catch (ServiceException ex) {
            log.error("[ParameterService][updateParameter] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ParameterController][updateParameter] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 删除参数
     *
     * @param request
     * @param parameter
     * @return
     */
    @SystemLog(name = "删除参数")
    @RequestMapping(value = "/parameter/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public JsonResult deleteParameter(HttpServletRequest request,
                                      @RequestBody SysParameter parameter) {
        try {
            SysUser user = this.getSessionSysUser(request);

            parameter.setUpdateBy(user.getUserId());
            parameter.setUpdateTime(new Date());
            parameter.setIsDel(CommonFainl.FAILSTATUS);
            int a = this.parameterService.updateParameter(parameter);
            return new JsonResult(JsonResultCode.SUCCESS, "删除成功", a);
        } catch (ServiceException ex) {
            log.error("[ParameterService][deleteParameter] ServiceException", ex);
            return new JsonResult(JsonResultCode.FAILURE, ex.getMessage(), "");
        } catch (Exception ex) {
            log.error("[ParameterController][deleteParameter] exception", ex);
            return new JsonResult(JsonResultCode.FAILURE, "系统错误,请联系管理员", "");
        }
    }

    /**
     * 查询参数是否存在
     */
    public boolean findByParam(SysParameter parameter) {
        SysParameter p = parameterService.findByParameter(parameter.getParmId());
        return p==null?true:false;
    }
}